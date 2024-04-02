package woowacourse.omok.local.presentation.ui

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
import woowacourse.omok.local.presentation.model.AppGameState
import woowacourse.omok.local.presentation.model.AppGameManager

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
        stones.forEach { stone ->
            playTurn { Coordinate(stone.x, stone.y) }
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
            val coordinate = index.toCoordinate()
            playTurn { coordinate }
        }
        if (gameManager.isFinish()) {
            showSnackbar(getString(R.string.finish_turn))
            return
        }
    }
    
    private fun playTurn(onCoordinate: () -> Coordinate) {
        if (gameManager.isRunning()) {
            runCatching {
                gameManager.playTurn(onCoordinate)
                printBoard(gameManager.board)
                printRunningInfo(gameManager.gameState)
            }.onFailure { throwable ->
                showSnackbar(getString(R.string.error_message, throwable.message))
            }
        }
    }
    
    private fun Int.toCoordinate(): Coordinate = Coordinate(this / BOARD_SIZE, this % BOARD_SIZE)
    
    private fun printRunningInfo(gameState: AppGameState) {
        when (gameState) {
            is AppGameState.Running.BlackTurn -> showSnackbar(getString(R.string.black_turn))
            is AppGameState.Running.WhiteTurn -> showSnackbar(getString(R.string.white_turn))
            is AppGameState.Finish -> showSnackbar(getString(R.string.finish_turn))
        }
    }
    
    private fun printBoard(board: Board) {
        allImageViews.forEachIndexed { index, imageView ->
            val x = index / BOARD_SIZE
            val y = index % BOARD_SIZE
            when (board.getBoardLayout()[x][y]) {
                PositionType.BLACK_STONE -> imageView.setImageResource(R.drawable.black_stone)
                PositionType.WHITE_STONE -> imageView.setImageResource(R.drawable.white_stone)
                PositionType.BLOCK -> imageView.setImageResource(R.drawable.block_stone)
                else -> imageView.setImageDrawable(null)
            }
        }
    }
    
    companion object {
        private const val BOARD_SIZE = 15
    }
}
