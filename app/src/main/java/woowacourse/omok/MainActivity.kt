package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import woowacourse.omok.database.OmokDao
import woowacourse.omok.model.Board
import woowacourse.omok.model.game.GameState
import woowacourse.omok.model.game.ViolationResult
import woowacourse.omok.model.stone.Point
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stone.StoneColor
import woowacourse.omok.model.stone.Stones

class MainActivity : AppCompatActivity() {
    private lateinit var board: Board
    private lateinit var omokDao: OmokDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val boardLayout = findViewById<TableLayout>(R.id.board)
        val stones: Stones = omokDao.readStones()
        val board = Board(stones)
        setBoardPoints(board, boardLayout)
        setTurnTextView(board)
        paintEntirePoints(boardLayout, omokDao.readStones())
    }

    override fun onDestroy() {
        omokDao.close()
        super.onDestroy()
    }

    private fun setBoardPoints(
        board: Board,
        boardLayout: TableLayout,
    ) = boardLayout
        .children
        .filterIsInstance<TableRow>()
        .forEachIndexed { rowIndex, tableRow ->
            tableRow.forEachIndexed { colIndex, imageView ->
                imageView.tag = Point(rowIndex + 1, colIndex + 1)
                imageView.setOnClickListener {
                    placeWithEndCheck(it.tag as Point, it as ImageView, board, boardLayout)
                }
            }
        }

    private fun place(
        point: Point,
        view: ImageView,
    ) {
        val stone = board.currentStone(point)
        board.place(stone)
        paintStone(stone, view)
        omokDao.insertStone(stone)
    }

    private fun placeWithEndCheck(
        point: Point,
        view: ImageView,
        board: Board,
        boardLayout: TableLayout,
    ) {
        val stone = board.currentStone(point)
        when (val violationResult = violationResult(stone)) {
            null -> {
                place(point, view)
            }
            is ViolationResult.InvalidMoveResult.FullBoard -> {
                showToastMessage(violationResult.message)
                inactivateBoard(boardLayout)
                omokDao.deleteStones()
            }
            else -> {
                showToastMessage(violationResult.message)
            }
        }

        if (omokGameState(stone) != GameState.PLAYING) {
            showToastMessage(omokGameState(stone).toWinnerMessage())
            inactivateBoard(boardLayout)
            omokDao.deleteStones()
        }
        setTurnTextView(board)
    }

    private fun inactivateBoard(boardLayout: TableLayout) {
        boardLayout
            .children
            .filterIsInstance<TableRow>()
            .forEach { tableRow ->
                tableRow.forEach { imageView ->
                    imageView.isClickable = false
                }
            }
    }

    private fun paintStone(
        stone: Stone,
        view: ImageView,
    ) {
        when (stone.color) {
            StoneColor.BLACK -> view.setImageResource(R.drawable.black_stone)
            StoneColor.WHITE -> view.setImageResource(R.drawable.white_stone)
        }
    }

    private fun paintEntirePoints(
        boardLayout: TableLayout,
        stones: Stones,
    ) {
        val omokStones: Set<Stone> = stones.stones
        omokStones.forEach { stone ->
            val imageView = boardLayout.findViewWithTag<ImageView>(stone.point)
            val imageResourceId =
                when (stone.color) {
                    StoneColor.BLACK -> R.drawable.black_stone
                    StoneColor.WHITE -> R.drawable.white_stone
                }
            imageView.setImageResource(imageResourceId)
        }
    }

    private fun setTurnTextView(board: Board) {
        val turnTextView = findViewById<TextView>(R.id.turnTextView)
        val text =
            when (
                board.stones.lastStone
                    ?.color
                    ?.reverse()
            ) {
                StoneColor.BLACK -> getString(R.string.message_show_black_turn)
                StoneColor.WHITE -> getString(R.string.message_show_white_turn)
                null -> getString(R.string.message_show_black_turn)
            }
        turnTextView.text = text
    }

    private fun violationResult(stone: Stone): ViolationResult? = board.checkViolation(stone)

    private fun omokGameState(stone: Stone): GameState = board.gameState(stone)

    private fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun GameState.toWinnerMessage(): String =
        when (this) {
            GameState.WHITE_OMOK -> getString(R.string.message_white_win)
            GameState.BLACK_OMOK -> getString(R.string.message_black_win)
            GameState.PLAYING -> ""
        }
}
