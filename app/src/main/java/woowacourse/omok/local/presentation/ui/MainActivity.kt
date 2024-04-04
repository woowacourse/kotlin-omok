package woowacourse.omok.local.presentation.ui

import android.graphics.Color
import android.util.Log
import android.widget.ImageView
import android.widget.TableRow
import androidx.core.view.children
import omok.model.Board
import omok.model.Coordinate
import omok.model.PositionType
import woowacourse.omok.R
import woowacourse.omok.databinding.ActivityMainBinding
import woowacourse.omok.local.db.StoneDaoImpl
import woowacourse.omok.local.presentation.base.BaseActivity
import woowacourse.omok.local.presentation.model.AppGameManager
import woowacourse.omok.local.presentation.model.AppGameState

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var allImageViews: List<ImageView>
    private val stoneDao = StoneDaoImpl(this)
    private lateinit var gameManager: AppGameManager

    override fun initializeViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreateSetup() {
        gameManager = AppGameManager(stoneDao)
        initializeGameBoard()
        initializeStonesFromDataBase()
        setImageViewsClickListener()
    }

    private fun initializeGameBoard() {
        allImageViews =
            binding.board
                .children
                .filterIsInstance<TableRow>()
                .flatMap { it.children }
                .filterIsInstance<ImageView>()
                .toList()
    }

    private fun initializeStonesFromDataBase() {
        val stones = stoneDao.findAll()
        stoneDao.drop()
        stones.forEach { stone ->
            runCatching {
                gameManager.playTurn { Coordinate(stone.x, stone.y) }
            }.onFailure { e ->
                Log.d("MainActivity", "Stone DB 오류: ${stone.x}, ${stone.y}", e)
            }
        }
        printBoard(gameManager.board)
    }

    private fun setImageViewsClickListener() {
        binding.restartButton.setOnClickListener {
            gameManager.restartGame()
            printBoard(gameManager.board)
        }
        allImageViews.forEachIndexed { index, view ->
            view.setOnClickListener {
                placeStoneAtTouchedCoordinate(index)
            }
        }
    }

    private fun placeStoneAtTouchedCoordinate(index: Int) {
        if (gameManager.isRunning()) {
            val coordinate = gameManager.coordinateFromIndex(index)
            runCatching {
                gameManager.playTurn { coordinate }
                printBoard(gameManager.board)
                printRunningInfo(gameManager.gameState)
            }.onFailure { throwable ->
                showSnackbar(getString(R.string.error_message, throwable.message))
            }
        }
        if (gameManager.isFinish()) {
            printRunningInfo(gameManager.gameState)
            return
        }
    }

    private fun printRunningInfo(gameState: AppGameState) {
        when (gameState) {
            is AppGameState.Running.BlackTurn -> {
                binding.turnInformation.text = getString(R.string.black_turn)
                binding.turnInformation.setTextColor(Color.BLACK)
            }
            is AppGameState.Running.WhiteTurn -> {
                binding.turnInformation.text = getString(R.string.white_turn)
                binding.turnInformation.setTextColor(Color.WHITE)
            }
            is AppGameState.Finish -> showSnackbar(getString(R.string.finish_turn))
        }
    }

    private fun printBoard(board: Board) {
        allImageViews.forEachIndexed { index, imageView ->
            val coordinate = gameManager.coordinateFromIndex(index)
            val x = coordinate.x
            val y = coordinate.y
            when (board.getBoardLayout()[x][y]) {
                PositionType.BLACK_STONE -> imageView.setImageResource(R.drawable.black_stone)
                PositionType.WHITE_STONE -> imageView.setImageResource(R.drawable.white_stone)
                PositionType.BLOCK -> imageView.setImageResource(R.drawable.block_stone)
                else -> imageView.setImageDrawable(null)
            }
        }
    }
}
