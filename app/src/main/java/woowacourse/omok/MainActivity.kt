package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omok.domain.BlackStone
import omok.domain.Board
import omok.domain.HorizontalAxis
import omok.domain.Player
import omok.domain.Position
import omok.domain.Stone
import omok.domain.WhiteStone
import omok.domain.judgement.LineJudgement
import omok.domain.state.State
import omok.domain.state.Turn
import omok.domain.state.Win
import woowacourse.omok.database.DBController
import woowacourse.omok.database.OmokConstract
import woowacourse.omok.database.OmokDBHelper

class MainActivity : AppCompatActivity() {
    private var omokBoard = Board(Player(), Player())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val board = findViewById<TableLayout>(R.id.board)

        initBoard(board)
        turn(Turn.Black, board)
    }

    private fun initBoard(board: TableLayout) {
        listOf(Turn.Black.color, Turn.White.color).forEach { color ->
            val indexes = DBController(OmokDBHelper(this).writableDatabase).getIndex(color)
            indexes.forEach { index -> initDisplay(board, color, index) }
        }
    }

    private fun initDisplay(board: TableLayout, color: String, index: Int) {
        board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { itIndex, view ->
                if (itIndex == index) {
                    initPut(index, color, view)
                }
            }
    }
    private fun initPut(index: Int, color: String, view: ImageView) {
        val position = positionFind(index)
        if (color == Turn.Black.color) {
            view.setImageResource(R.drawable.black_stone)
            omokBoard.blackPlayer.put(BlackStone(position))
        }
        if (color == Turn.White.color) {
            view.setImageResource(R.drawable.white_stone)
            omokBoard.whitePlayer.put(WhiteStone(position))
        }
        omokBoard.occupyPosition(position)
    }

    private fun turn(state: State, board: TableLayout) {
        when (state) {
            Turn.Black -> putBoard(board, state)
            Turn.White -> putBoard(board, state)
            Win.Black -> {
                val intent = Intent(this, WinActivity::class.java)
                intent.putExtra(OmokConstract.TABLE_COLUMN_COLOR, Turn.Black.color)
                startActivity(intent)
            }
            Win.White -> {
                val intent = Intent(this, WinActivity::class.java)
                intent.putExtra(OmokConstract.TABLE_COLUMN_COLOR, Turn.White.color)
                startActivity(intent)
            }
        }
    }

    private fun putBoard(board: TableLayout, turn: State) {
        board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                view.setOnClickListener {
                    if (turn == Turn.Black) {
                        blackTurn(board, index, view)
                    }
                    if (turn == Turn.White) {
                        whiteTurn(board, index, view)
                    }
                }
            }
    }

    private fun blackTurn(board: TableLayout, index: Int, view: ImageView) {
        val position = positionFind(index)
        if (!omokBoard.isBlackPlaceable(position)) {
            Toast.makeText(applicationContext, "해당 자리에 둘 수 없습니다.", Toast.LENGTH_SHORT).show()
            turn(Turn.Black, board)
        }
        if (omokBoard.isBlackPlaceable(position)) {
            DBController(OmokDBHelper(this).writableDatabase).insertDB(Turn.Black.color, index)
            view.setImageResource(R.drawable.black_stone)
            omokBoard.blackPlayer.put(BlackStone(position))
            omokBoard.occupyPosition(position)
            if (lineJudge(omokBoard.blackPlayer, BlackStone(position))) {
                turn(Win.Black, board)
            }
            if (!lineJudge(omokBoard.blackPlayer, BlackStone(position))) {
                turn(Turn.White, board)
            }
        }
    }

    private fun whiteTurn(board: TableLayout, index: Int, view: ImageView) {
        val position = positionFind(index)
        if (!omokBoard.isWhitePlaceable(position)) {
            Toast.makeText(applicationContext, "해당 자리에 둘 수 없습니다.", Toast.LENGTH_SHORT).show()
            turn(Turn.White, board)
        }
        if (omokBoard.isBlackPlaceable(position)) {
            DBController(OmokDBHelper(this).writableDatabase).insertDB(Turn.White.color, index)
            view.setImageResource(R.drawable.white_stone)
            omokBoard.whitePlayer.put(WhiteStone(position))
            omokBoard.occupyPosition(position)
            if (lineJudge(omokBoard.whitePlayer, WhiteStone(position))) {
                turn(Win.White, board)
            }
            if (!lineJudge(omokBoard.whitePlayer, WhiteStone(position))) {
                turn(Turn.Black, board)
            }
        }
    }

    private fun positionFind(index: Int): Position {
        val x = (index % Position.MAX_VERTICAL_AXIS) + 1
        val y = Position.MAX_VERTICAL_AXIS - index / Position.MAX_VERTICAL_AXIS
        return Position(HorizontalAxis.getHorizontalAxis(x), y)
    }

    private fun lineJudge(player: Player, stone: Stone) = LineJudgement(player, stone).check()
}
