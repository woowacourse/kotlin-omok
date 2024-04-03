package woowacourse.omok.game.controller

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.google.android.material.snackbar.Snackbar
import omok.model.Board
import omok.model.Color
import omok.model.Point
import omok.model.RenjuRuleAdapter
import omok.model.Stone
import omok.model.Turn
import woowacourse.omok.R
import woowacourse.omok.game.database.OmokEntry
import woowacourse.omok.game.database.OmokEntryDao

class MainActivity : AppCompatActivity() {
    private val board2 = Board(15, RenjuRuleAdapter())
    private val dao by lazy { OmokEntryDao(this) }
    private val bannedPoints = mutableListOf<Point>()
    private lateinit var textView: TextView
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
        textView = findViewById(R.id.textView)
        button = findViewById(R.id.putBtn)

        fetchSavedData(board)
        val turn = Turn(board2.lastColor())
        turn.next()

        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                val (row, col) = listOf(index / 15, index % 15)
                val point = Point(row, col)
                putStone(view, point, turn, board)
            }
    }

    private fun putStone(
        view: ImageView,
        point: Point,
        turn: Turn,
        board: TableLayout,
    ) {
        view.setOnClickListener {
            if (board2.pointEmpty(point) && board2.isValidPoint(turn, point)) {
                view.setImageResource(turnToStone(turn))
                board2.add(Stone(point, turn.color()))
                saveStoneData(Stone(point, turn.color()))
                resetBanned(board)
                if (board2.checkEndCondition()) {
                    resetAll(board, view, turn)
                }
                turn.next()
            } else {
                handleInvalidPoint(turn, point, view)
            }
        }
    }

    private fun handleInvalidPoint(
        turn: Turn,
        point: Point,
        view: ImageView
    ) {
        if (!board2.isValidPoint(turn, point)) {
            // 렌주룰 위반
            // Activity 가 룰 위반인 것을 알고 있어도 되는가? -> 모델의 역할 아닌가?
            view.setImageResource(R.drawable.unavailable_point)
            Toast.makeText(this, "둘 수 없는 자리입니다.", Toast.LENGTH_SHORT).show()
            bannedPoints.add(point)
        } else {
            Toast.makeText(this, "둘 수 없는 자리입니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetBanned(board: TableLayout) {
        while (bannedPoints.size > 0) {
            val currentPoint = bannedPoints.removeAt(0)
            val (row, col) = listOf(currentPoint.row, currentPoint.col)
            board
                .children
                .filterIsInstance<TableRow>()
                .flatMap { it.children }
                .filterIsInstance<ImageView>()
                .forEachIndexed { index, view ->
                    if (index == row * 15 + col) view.setImageDrawable(null)
                }
        }
    }

    private fun resetAll(
        board: TableLayout,
        view: ImageView,
        turn: Turn,
    ) {
        resetBoardView(board)
        board2.resetCustomBoard()
        deleteAllData()
        popUpSnackBar(view, turn, board)
        turn.reset()
    }

    private fun popUpSnackBar(
        view: ImageView,
        turn: Turn,
        board: TableLayout
    ) {
        val snackBar =
            Snackbar.make(
                view,
                "게임 종료!  ${turnToString(turn)}의 승리입니다!",
                Snackbar.LENGTH_INDEFINITE,
            )
        board.setOnClickListener(null)
        snackBar.setAction("확인") {
            //resetBoardView(board)
            snackBar.dismiss()
        }
        snackBar.show()
    }

    private fun turnToStone(turn: Turn): Int {
        return if (turn.isBlack()) {
            R.drawable.black_stone
        } else {
            R.drawable.white_stone
        }
    }

    private fun turnToString(turn: Turn): String = colorToString(turn.color())

    private fun colorToString(color: Color): String = if (color == Color.BLACK) "흑" else "백"

    private fun stringToTurn(color: String): Color = if (color == "흑") Color.BLACK else Color.WHITE

    private fun saveStoneData(stone: Stone) {
        dao.save(OmokEntry(null, stone.point.row, stone.point.col, colorToString(stone.color)))
    }

    private fun fetchSavedData(board: TableLayout) {
        val data = dao.findAll()
        setUpCustomBoard(board, data)
    }

    private fun setUpCustomBoard(
        board: TableLayout,
        omokData: List<OmokEntry>,
    ) {
        omokData.forEach { it ->
            val color = stringToTurn(it.color)
            board2.add(Stone(Point(it.row, it.col), color))

            val currentIndex = it.row * 15 + it.col
            board.children
                .filterIsInstance<TableRow>()
                .flatMap { it.children }
                .filterIsInstance<ImageView>()
                .forEachIndexed { index, imageView ->
                    if (index == currentIndex) {
                        imageView.setImageResource(turnToStone(Turn(color)))
                    }
                }
        }
    }

    private fun resetBoardView(board: TableLayout) {
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { view ->
                view.setImageDrawable(null)
            }
    }

    private fun deleteAllData() {
        dao.drop()
    }
}
