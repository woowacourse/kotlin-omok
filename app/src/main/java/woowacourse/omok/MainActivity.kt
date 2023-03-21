package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omok.domain.Turn
import omok.domain.board.Board
import omok.domain.board.Position
import omok.domain.judgment.BlackReferee
import omok.domain.judgment.ResultReferee
import omok.domain.player.Black
import omok.domain.player.White

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val domainBoard = Board()
        val domainTurn = Turn(setOf(Black, White))

        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                view.setOnClickListener(takeTurn(index, domainBoard, domainTurn, view))
            }
    }

    private fun takeTurn(index: Int, board: Board, turn: Turn, view: ImageView) = OnClickListener {
        val position = getPosition(index)
        Log.d("test_position", position.toString())
        Log.d("test_turn", turn.now.javaClass.simpleName.toString())
        val result = placeStone(board, turn, position)
        result
            .onSuccess {
                changeImage(turn, view)
                judgeWinner(board, turn, position)
                turn.changeTurn()
            }
            .onFailure { error: Throwable -> showAlertDialog(error.message ?: "") }
    }

    private fun getPosition(index: Int): Position {
        val columnAxis = index % 15
        val rowAxis = 15 - (index / 15) - 1
        return Position(columnAxis, rowAxis)
    }

    private fun placeStone(board: Board, turn: Turn, position: Position): Result<Unit> {
        return runCatching {
            board.placeStone(position, turn.now, referee = BlackReferee())
        }
    }

    private fun changeImage(turn: Turn, view: ImageView) {
        when (turn.now) {
            Black -> view.setImageResource(R.drawable.black_stone)
            White -> view.setImageResource(R.drawable.white_stone)
        }
    }

    private fun showAlertDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setIcon(R.drawable.error_24)
        builder.setTitle("돌을 놓을 수 없습니다!")
        builder.setMessage(message)
        builder.setPositiveButton("확인", null)
        builder.show()
    }

    private fun judgeWinner(board: Board, turn: Turn, position: Position) {
        if (ResultReferee().checkWinner(board.positions, position)) {
            val intent = Intent(this, WinnerActivity::class.java)
            intent.putExtra("winner", turn.now.toString())
            startActivity(intent)
        }
    }
}
