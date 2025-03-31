package woowacourse.omok

import android.os.Bundle
import android.widget.Button
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
import androidx.core.view.forEachIndexed
import woowacourse.omok.database.DbHelper
import woowacourse.omok.database.OmokDao
import woowacourse.omok.model.Board
import woowacourse.omok.model.game.GameState
import woowacourse.omok.model.game.ViolationResult
import woowacourse.omok.model.stone.Point
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stone.StoneColor
import woowacourse.omok.model.stone.Stones

class MainActivity : AppCompatActivity() {
    private val omokDao: OmokDao = OmokDao(DbHelper(this))

    private lateinit var board: Board
    private lateinit var turnTextView: TextView
    private lateinit var resetBtn: Button
    private var boardPointImageViews: Map<Point, ImageView> = mapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initBoard()
        initViews()
        setBoardPointClickListeners()
        setTurnTextView()
        paintEntirePoints()
    }

    override fun onDestroy() {
        omokDao.close()
        super.onDestroy()
    }

    private fun initBoard() {
        val stones: Stones = omokDao.readStones()
        board = Board(stones)
    }

    private fun initViews() {
        val boardTableLayout = findViewById<TableLayout>(R.id.board)
        initBoardPointViews(boardTableLayout)

        turnTextView = findViewById<TextView>(R.id.turnTextView)

        resetBtn = findViewById<Button>(R.id.resetBtn)
        resetBtn.setOnClickListener { reset() }
    }

    private fun reset() {
        omokDao.deleteStones()
        initBoard()
        clearBoardImageViews()
        paintEntirePoints()
        setTurnTextView()
        setBoardClickability(true)
    }

    private fun initBoardPointViews(boardTableLayout: TableLayout) {
        boardTableLayout
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, tableRow ->
                tableRow.forEachIndexed { colIndex, imageView ->
                    imageView.tag = Point(rowIndex + 1, colIndex + 1)
                    boardPointImageViews += imageView.tag as Point to imageView as ImageView
                }
            }
    }

    private fun setBoardPointClickListeners() {
        boardPointImageViews.values.forEach {
            it.setOnClickListener {
                placeWithCheck(it.tag as Point, board)
            }
        }
    }

    private fun place(point: Point) {
        val stone = board.currentStone(point)
        board.place(stone)
        paintStone(stone, boardPointImageViews[point] ?: throw IllegalStateException())
        omokDao.insertStone(stone)
    }

    private fun placeWithCheck(
        point: Point,
        board: Board,
    ) {
        val stone = board.currentStone(point)
        when (val violationResult = board.checkViolation(stone)) {
            ViolationResult.Success -> place(point)
            is ViolationResult.Failure.InvalidMoveResult.FullBoard -> {
                showToastMessage(violationResult.message)
                setBoardClickability(false)
                omokDao.deleteStones()
            }

            is ViolationResult.Failure -> showToastMessage(violationResult.message)
        }

        if (board.gameState(stone) != GameState.PLAYING) {
            showToastMessage(board.gameState(stone).toWinnerMessage())
            setBoardClickability(false)
            omokDao.deleteStones()
        }
        setTurnTextView()
    }

    private fun setBoardClickability(isClickable: Boolean) {
        boardPointImageViews.values.forEach {
            it.isClickable = isClickable
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

    private fun paintEntirePoints() {
        val omokStones: Set<Stone> = omokDao.readStones().stones
        omokStones.forEach { stone ->
            val imageView: ImageView =
                boardPointImageViews[stone.point] ?: throw IllegalStateException()
            val imageResourceId =
                when (stone.color) {
                    StoneColor.BLACK -> R.drawable.black_stone
                    StoneColor.WHITE -> R.drawable.white_stone
                }
            imageView.setImageResource(imageResourceId)
        }
    }

    private fun clearBoardImageViews() {
        boardPointImageViews.values.forEach {
            val point = it.tag as Point
            val imageResource: Int =
                when {
                    point == Point(1, 1) -> R.drawable.board_top_left
                    point == Point(1, 15) -> R.drawable.board_top_right
                    point == Point(15, 1) -> R.drawable.board_bottom_left
                    point == Point(15, 15) -> R.drawable.board_bottom_right

                    point.row == 1 -> R.drawable.board_top
                    point.row == 15 -> R.drawable.board_bottom
                    point.col == 1 -> R.drawable.board_left
                    point.col == 15 -> R.drawable.board_right

                    else -> R.drawable.board_center
                }
            it.setImageResource(imageResource)
        }
    }

    private fun setTurnTextView() {
        turnTextView.text =
            when (board.stones.currentStoneColor()) {
                StoneColor.BLACK -> getString(R.string.message_show_black_turn)
                StoneColor.WHITE -> getString(R.string.message_show_white_turn)
            }
    }

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
