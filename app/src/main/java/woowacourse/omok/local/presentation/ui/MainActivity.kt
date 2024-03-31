package woowacourse.omok.local.presentation.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.google.android.material.snackbar.Snackbar
import omok.model.Board
import omok.model.Coordinate
import omok.model.PositionType
import woowacourse.omok.R
import woowacourse.omok.databinding.ActivityMainBinding
import woowacourse.omok.local.db.StoneDaoImpl
import woowacourse.omok.local.presentation.model.AppGameState
import woowacourse.omok.local.presentation.model.StoneEntity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var allImageViews: List<ImageView>
    private val board = Board()
    private var gameState: AppGameState = AppGameState.Running.BlackTurn(board)
    private val dao = StoneDaoImpl(this)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeGameBoard()
        loadGameFromDatabase()
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
    
    private fun loadGameFromDatabase() {
        val stones = dao.findAll()
        stones.forEach { stone ->
            board.placeStone(Coordinate(stone.x, stone.y), stone.positionType)
        }
        printBoard(board)
    }
    
    private fun setImageViewsClickListener() {
        binding.restartButton.setOnClickListener {
            dao.drop()
            board.clear()
            gameState = AppGameState.Running.BlackTurn(board)
            printBoard(board)
        }
        allImageViews.forEachIndexed { index, view ->
            view.setOnClickListener {
                handleUserInput(index)
            }
        }
    }
    
    private fun handleUserInput(index: Int) {
        if (isRunning()) {
            val coordinate = index.toCoordinate()
            playTurn { coordinate }
        }
        if (gameState is AppGameState.Finish) {
            showSnackbar("게임이 종료되었습니다.")
            return
        }
    }
    
    private fun currentType(gameState: AppGameState): PositionType {
        return when (gameState) {
            is AppGameState.Running -> gameState.currentType()
            else -> PositionType.EMPTY
        }
    }
    
    private fun playTurn(onCoordinate: () -> Coordinate) {
        val currentPositionType = currentType(gameState)
        if (isRunning()) {
            runCatching {
                val lastCoordinate = onCoordinate()
                gameState = gameState.updateState { lastCoordinate }
                
                if (gameState is AppGameState.Finish || gameState is AppGameState.Running) {
                    saveStoneToDatabase(lastCoordinate, currentPositionType)
                }
                
                printBoard(board)
                printRunningInfo(gameState)
            }.onFailure { throwable ->
                showSnackbar(ERROR_MESSAGE.format(throwable.message))
            }
        }
    }
    
    
    private fun saveStoneToDatabase(coordinate: Coordinate, positionType: PositionType) {
        val stoneEntity = StoneEntity(0L, coordinate.x, coordinate.y, positionType)
        dao.save(stoneEntity)
    }
    
    private fun isRunning() = gameState is AppGameState.Running
    
    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
    
    private fun Int.toCoordinate(): Coordinate = Coordinate(this / BOARD_SIZE, this % BOARD_SIZE)
    
    private fun printRunningInfo(gameState: AppGameState) {
        when (gameState) {
            is AppGameState.Running.BlackTurn -> showSnackbar(BLACK_TURN)
            is AppGameState.Running.WhiteTurn -> showSnackbar(WHITE_TURN)
            is AppGameState.Finish -> showSnackbar(FINISH_TURN)
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
        private const val ERROR_MESSAGE = "에러 발생 : %s \n다시 시도해주세요."
        private const val BOARD_SIZE = 15
        private const val BLACK_TURN = "흑돌 차례입니다."
        private const val WHITE_TURN = "백돌 차례입니다."
        private const val FINISH_TURN = "오목이 완성되어 게임이 종료되었습니다."
    }
}
