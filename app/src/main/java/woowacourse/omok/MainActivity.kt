package woowacourse.omok

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omok.Board
import omok.HorizontalAxis
import omok.OmokGame
import omok.Player
import omok.Position
import omok.Stone
import omok.state.State
import omok.state.Turn
import omok.state.Win

class MainActivity : AppCompatActivity() {

    lateinit var omokGame: OmokGame

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()

        val db = OmokDBHelper(this).writableDatabase
        setOmokGame(db)
        setBoardView(board)

        var state: State = Turn.Black

        board.forEachIndexed { index, view ->
            view.setOnClickListener {
                state = gameOn(index, view, state as Turn, db)
                if (state is Win) {
                    gameOver(state as Win, db)
                }
            }
        }
    }

    private fun setOmokGame(db: SQLiteDatabase) {
        val sql = "SELECT * FROM ${OmokContract.TABLE_NAME}"
        val cursor: Cursor = db.rawQuery(sql, null)
        val blackPlayer = Player()
        val whitePlayer = Player()
        var turn: String? = null

        while (cursor.moveToNext()) {
            with(cursor) {
                turn = getString(getColumnIndexOrThrow(OmokContract.TURN))
                if (turn == "black") {
                    blackPlayer.hand.add(
                        Stone(
                            Position(
                                HorizontalAxis.getHorizontalAxis((getInt(getColumnIndexOrThrow(OmokContract.POSITION_X)))),
                                getInt(getColumnIndexOrThrow(OmokContract.POSITION_Y))
                            )
                        )
                    )
                }
                if (turn == "white") {
                    whitePlayer.hand.add(
                        Stone(
                            Position(
                                HorizontalAxis.getHorizontalAxis((getInt(getColumnIndexOrThrow(OmokContract.POSITION_X)))),
                                getInt(getColumnIndexOrThrow(OmokContract.POSITION_Y))
                            )
                        )
                    )
                }
            }
        }
        omokGame = OmokGame(Board(blackPlayer, whitePlayer))
    }

    private fun setBoardView(board: Sequence<ImageView>) {
        omokGame.board.blackPlayer.hand.stones.forEach { stone ->
            board.toList()[transformStonePositionToView(stone)].setImageResource(R.drawable.black_stone_nabi)
        }
        omokGame.board.whitePlayer.hand.stones.forEach { stone ->
            board.toList()[transformStonePositionToView(stone)].setImageResource(R.drawable.white_stone_choonbae)
        }
    }

    private fun transformStonePositionToView(stone: Stone): Int {
        return (stone.position.horizontalAxis.axis - 1) * 15 + (stone.position.verticalAxis - 1)
    }

    private fun gameOn(index: Int, view: ImageView, turn: Turn, db: SQLiteDatabase): State {
        val position = Position(HorizontalAxis.getHorizontalAxis(index / 15 + 1), index % 15 + 1)
        if (!omokGame.board.isPlaceable(turn, position)) {
            Toast.makeText(this, "해당 자리에 돌을 둘 수 없습니다.", Toast.LENGTH_LONG).show()
            return turn
        }
        putStone(position, turn, db)

        when (turn) {
            Turn.Black -> view.setImageResource(R.drawable.black_stone_nabi)
            Turn.White -> view.setImageResource(R.drawable.white_stone_choonbae)
        }
        return when (turn) {
            Turn.Black -> omokGame.blackTurn(position)
            Turn.White -> omokGame.whiteTurn(position)
        }
    }

    private fun gameOver(win: Win, db: SQLiteDatabase) {
        val winMessage = if (win == Win.White) "백의 승리입니다." else "흑의 승리입니다."
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("축하합니다")
            .setMessage(winMessage)
            .setPositiveButton("다시 시작") { dialog, which ->
                finishAffinity()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                System.exit(0)
            }
            .setNeutralButton("게임 종료") { dialog, which -> finish() }
            .create()

        db.delete(OmokContract.TABLE_NAME, null, null)

        alertDialog.show()
    }

    private fun putStone(position: Position, turn: Turn, db: SQLiteDatabase) {
        val values = ContentValues()
        with(values) {
            put(OmokContract.POSITION_X, position.horizontalAxis.axis)
            put(OmokContract.POSITION_Y, position.verticalAxis)
            put(OmokContract.TURN, turn.toString())
        }
        db.insert(OmokContract.TABLE_NAME, null, values)
    }
}
