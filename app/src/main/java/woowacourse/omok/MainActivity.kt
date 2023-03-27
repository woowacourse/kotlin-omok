package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omok.HorizontalAxis
import omok.OmokGame
import omok.Position
import omok.state.Fail
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

        val dbHelper = OmokDBHelper(this)
        omokGame = dbHelper.setOmokGame()
        setBoardView(board)

        var state: State = Turn.Black

        board.forEachIndexed { index, view ->
            view.setOnClickListener {
                state = gameOn(index.toPosition(), view, state as Turn, dbHelper)
                if (state is Win) {
                    gameOver(state as Win, dbHelper)
                }
            }
        }
    }

    private fun Int.toPosition(): Position {
        return Position(HorizontalAxis.getHorizontalAxis(this / 15 + 1), this % 15 + 1)
    }

    private fun setBoardView(board: Sequence<ImageView>) {
        omokGame.board.blackPlayer.stones.forEach { stone ->
            board.toList()[stone.toViewPosition()].setImageResource(R.drawable.black_stone_nabi)
        }
        omokGame.board.whitePlayer.stones.forEach { stone ->
            board.toList()[stone.toViewPosition()].setImageResource(R.drawable.white_stone_choonbae)
        }
    }

    private fun gameOn(position: Position, view: ImageView, turn: Turn, db: OmokDBHelper): State {
        val state = omokGame.takeTurn(turn, position)
        if (state is Fail) {
            Toast.makeText(this, "해당 자리에 돌을 둘 수 없습니다.", Toast.LENGTH_LONG).show()
            return turn
        }

        db.insertStone(position, turn)
        when (turn) {
            Turn.Black -> view.setImageResource(R.drawable.black_stone_nabi)
            Turn.White -> view.setImageResource(R.drawable.white_stone_choonbae)
        }
        return state
    }

    private fun gameOver(win: Win, db: OmokDBHelper) {
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
            .setCancelable(false)
            .create()

        db.deleteAll()

        alertDialog.show()
    }
}
