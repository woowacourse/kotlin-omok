package woowacourse.omok

import android.content.ContentValues
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
import woowacourse.omok.database.OmokConstract
import woowacourse.omok.database.OmokDBHelper

class MainActivity : AppCompatActivity() {
    private val omokBoard = Board(Player(), Player())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
        putBoard(board)
        turn(Turn.Black, board)
    }

    private fun blackTurn(board: TableLayout) {
        var position: Position
        val db = OmokDBHelper(this)
        val wDb = db.writableDatabase
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                view.setOnClickListener {
                    position = positionFind(index)
                    if (!omokBoard.isBlackPlaceable(position)) {
                        Toast.makeText(applicationContext, "해당 자리에 둘 수 없습니다.", Toast.LENGTH_LONG).show()
                        turn(Turn.Black, board)
                    } else {
                        val values = ContentValues()
                        values.put("color", "black")
                        values.put("position", index)

                        wDb.insert(OmokConstract.TABLE_NAME, null, values)

                        view.setImageResource(R.drawable.black_stone)
                        omokBoard.blackPlayer.put(BlackStone(position))
                        omokBoard.occupyPosition(position)
                        if (lineJudge(omokBoard.blackPlayer, BlackStone(position))) turn(Win.Black, board)
                        else turn(Turn.White, board)
                    }
                }
            }
    }

    private fun whiteTurn(board: TableLayout) {
        var position: Position
        val db = OmokDBHelper(this)
        val wDb = db.writableDatabase
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                view.setOnClickListener {
                    position = positionFind(index)
                    if (!omokBoard.isWhitePlaceable(position)) {
                        Toast.makeText(applicationContext, "해당 자리에 둘 수 없습니다.", Toast.LENGTH_LONG).show()
                        turn(Turn.White, board)
                    } else {
                        val values = ContentValues()
                        values.put("color", "white")
                        values.put("position", index)

                        wDb.insert(OmokConstract.TABLE_NAME, null, values)

                        view.setImageResource(R.drawable.white_stone)
                        omokBoard.whitePlayer.put(WhiteStone(position))
                        omokBoard.occupyPosition(position)
                        if (lineJudge(omokBoard.whitePlayer, WhiteStone(position))) turn(Win.White, board)
                        else turn(Turn.Black, board)
                    }
                }
            }
    }

    private fun turn(state: State, board: TableLayout) {
        when (state) {
            Turn.Black -> blackTurn(board)
            Turn.White -> whiteTurn(board)
            Win.Black -> Toast.makeText(applicationContext, "흑이 승리했습니다.", Toast.LENGTH_LONG).show()
            Win.White -> Toast.makeText(applicationContext, "백이 승리했습니다.", Toast.LENGTH_LONG).show()
        }
    }

    private fun positionFind(index: Int): Position {
        val x = (index % 15) + 1
        val y = 15 - index / 15
        return Position(HorizontalAxis.getHorizontalAxis(x), y)
    }

    private fun lineJudge(player: Player, stone: Stone) = LineJudgement(player, stone).check()

    fun putBoard(board: TableLayout) {
        val db = OmokDBHelper(this)
        val wDb = db.writableDatabase

        var color = "black"
        var cursor = wDb.rawQuery("SELECT * FROM ${OmokConstract.TABLE_NAME} WHERE ${OmokConstract.TABLE_COLUMN_COLOR} = '" + color + "';", null)

        with(cursor) {
            while (moveToNext()) {
                val index = getInt(getColumnIndexOrThrow(OmokConstract.TABLE_COLUMN_POSITION))
                board
                    .children
                    .filterIsInstance<TableRow>()
                    .flatMap { it.children }
                    .filterIsInstance<ImageView>()
                    .forEachIndexed { itIndex, view ->
                        if (itIndex == index) {
                            val position = positionFind(index)
                            view.setImageResource(R.drawable.black_stone)
                            omokBoard.blackPlayer.put(BlackStone(position))
                            omokBoard.occupyPosition(position)
                        }
                    }
            }
        }

        color = "white"
        cursor = wDb.rawQuery("SELECT * FROM ${OmokConstract.TABLE_NAME} WHERE ${OmokConstract.TABLE_COLUMN_COLOR} = '" + color + "';", null)

        with(cursor) {
            while (moveToNext()) {
                val index = getInt(getColumnIndexOrThrow(OmokConstract.TABLE_COLUMN_POSITION))
                board
                    .children
                    .filterIsInstance<TableRow>()
                    .flatMap { it.children }
                    .filterIsInstance<ImageView>()
                    .forEachIndexed { itIndex, view ->
                        if (itIndex == index) {
                            val position = positionFind(index)
                            view.setImageResource(R.drawable.white_stone)
                            omokBoard.whitePlayer.put(WhiteStone(position))
                            omokBoard.occupyPosition(position)
                        }
                    }
            }
        }
        cursor.close()
    }
}
