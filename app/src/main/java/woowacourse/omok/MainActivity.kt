package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val omokGame = OmokGame(Board(rule = RenjuRuleAdapter()))
        var winner: Color? = null
        val board = findViewById<TableLayout>(R.id.board)

        getAllCoordinateView(board).forEachIndexed { index, view ->
            view.setOnClickListener {
                if (winner == null) {
                    winner = omokGame.getWinnerColorPhase(
                        { drawStone(view, omokGame.currentColor) },
                        { getPosition(index) }
                    )
                }
                winner?.let { showWinner(it) }
            }
        }
    }

    private fun getAllCoordinateView(board: TableLayout): List<ImageView> {
        return board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .toList()
    }

    private fun drawStone(view: ImageView, color: Color) {
        view.setImageResource(getStoneImage(color))
    }

    private fun getPosition(number: Int): Position {
        val boardSize = Board.getSize()
        val x = number / boardSize
        val y = number % boardSize
        return Position(x, y)
    }

    private fun getStoneImage(color: Color): Int {
        return when (color) {
            Color.BLACK -> R.drawable.black_stone
            Color.WHITE -> R.drawable.white_stone
        }
    }

    private fun showWinner(color: Color?) {
        return when (color) {
            Color.BLACK -> showToastMessage(WINNER_MESSAGE.format("흑"))
            Color.WHITE -> showToastMessage(WINNER_MESSAGE.format("백"))
            null -> {}
        }
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val WINNER_MESSAGE = "%s의 승리입니다"
    }
}
