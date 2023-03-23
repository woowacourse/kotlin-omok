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
import omok.state.State
import omok.state.Turn
import omok.state.Win

class MainActivity : AppCompatActivity() {
    val omokGame = OmokGame()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var state: State = Turn.Black
        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                view.setOnClickListener {
                    state = gameOn(index, view, state as Turn)
                    if (state is Win) {
                        gameOver(state as Win)
                    }
                }
            }
    }

    private fun gameOn(index: Int, view: ImageView, turn: Turn): State {
        val position = Position(HorizontalAxis.getHorizontalAxis(index / 15 + 1), index % 15 + 1)
        if (!omokGame.board.isPlaceable(turn, position)) {
            Toast.makeText(this, "해당 자리에 돌을 둘 수 없습니다.", Toast.LENGTH_LONG).show()
            return turn
        }
        when (turn) {
            Turn.Black -> view.setImageResource(R.drawable.black_stone_nabi)
            Turn.White -> view.setImageResource(R.drawable.white_stone_choonbae)
        }
        return when (turn) {
            Turn.Black -> omokGame.blackTurn(position)
            Turn.White -> omokGame.whiteTurn(position)
        }
    }

    private fun gameOver(win: Win) {
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

        alertDialog.show()
    }
}
