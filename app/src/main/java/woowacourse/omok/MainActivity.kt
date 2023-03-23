package woowacourse.omok

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.OmokGame
import domain.stone.Color
import domain.stone.Position

class MainActivity : AppCompatActivity() {
    private val omokGame: OmokGame = OmokGame()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)

        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                view.setOnClickListener {
                    if (omokGame.turn.boardState.isFinished().not()) positionClick(index, view)
                }
            }
    }

    private fun positionClick(index: Int, view: ImageView) {
        val position = convertIndexToPosition(index)
        val playTurnColor = omokGame.playTurn(position)
        if (playTurnColor == null && omokGame.turn.boardState.isFinished().not()) {
            toast("해당 위치에는 놓을 수 없습니다.")
        } else if (playTurnColor == null) {
            toast("이미 게임이 종료됐습니다.")
        } else {
            setStone(playTurnColor, view)
        }
        if (omokGame.turn.boardState.isFinished()) {
            toast("게임이 종료됐습니다.")
            // 다른 화면으로 이동?
        }
    }

    private fun convertIndexToPosition(index: Int): Position {
        val row = 14 - (index / 15)
        val column = index % 15
        Log.d("kmj", "index: $index , column: $column , row: $row")
        return Position(column + 1, row + 1)
    }

    private fun setStone(color: Color, view: ImageView) {
        when (color) {
            Color.BLACK -> setBlackStone(view)
            Color.WHITE -> setWhiteStone(view)
        }
    }

    private fun setBlackStone(view: ImageView) {
        view.setImageResource(R.drawable.black_stone)
    }

    private fun setWhiteStone(view: ImageView) {
        view.setImageResource(R.drawable.white_stone)
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
