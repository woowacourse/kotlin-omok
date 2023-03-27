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
                state = gameOn(index, view, state as Turn, dbHelper)
                if (state is Win) {
                    gameOver(state as Win, dbHelper)
                }
            }
        }
    }

    private fun setBoardView(board: Sequence<ImageView>) {
        omokGame.board.blackPlayer.stones.forEach { stone ->
            board.toList()[stone.toViewPosition()].setImageResource(R.drawable.black_stone_nabi)
        }
        omokGame.board.whitePlayer.stones.forEach { stone ->
            board.toList()[stone.toViewPosition()].setImageResource(R.drawable.white_stone_choonbae)
        }
    }

    private fun gameOn(index: Int, view: ImageView, turn: Turn, db: OmokDBHelper): State {
        val position = Position(HorizontalAxis.getHorizontalAxis(index / 15 + 1), index % 15 + 1)
        if (!omokGame.board.isPlaceable(turn, position)) {
            Toast.makeText(this, "해당 자리에 돌을 둘 수 없습니다.", Toast.LENGTH_LONG).show()
            return turn
        }
        db.insertStone(position, turn)

        return when (turn) {
            Turn.Black -> view.run {
                setImageResource(R.drawable.black_stone_nabi)
                omokGame.blackTurn(position)
            }
            Turn.White -> view.run {
                setImageResource(R.drawable.white_stone_choonbae)
                omokGame.whiteTurn(position)
            }
        }
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
            .create()

        db.deleteAll()

        alertDialog.show()
    }
}
