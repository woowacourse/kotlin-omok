package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
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
                view.setOnClickListener { state = takeTurn(index, view, state) }
            }
    }

    private fun takeTurn(index: Int, view: ImageView, state: State): State {
        return when (state) {
            Turn.Black -> gameOn(index, view, state as Turn)
            Turn.White -> gameOn(index, view, state as Turn)
            Win.Black -> gameOver(Win.Black)
            Win.White -> gameOver(Win.White)
        }
    }

    private fun gameOn(index: Int, view: ImageView, turn: Turn): State {
        val position = Position(HorizontalAxis.getHorizontalAxis(index / 15 + 1), index % 15 + 1)
        if (!omokGame.board.isPlaceable(turn, position)) {
            // warning
            return turn
        }
        when (turn) {
            Turn.Black -> view.setImageResource(R.drawable.black_stone)
            Turn.White -> view.setImageResource(R.drawable.white_stone)
        }
        return when (turn) {
            Turn.Black -> omokGame.blackTurn(position)
            Turn.White -> omokGame.whiteTurn(position)
        }
    }

    private fun gameOver(win: Win): Win {
        return win
    }
}
