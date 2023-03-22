package woowacourse.omok

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.OmokGame
import domain.OmokGameState
import domain.board.Board
import domain.stone.Point

class MainActivity : AppCompatActivity() {

    private lateinit var boards: List<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val omokGame = OmokGame()

        boards = findViewById<TableLayout>(R.id.board)
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .toList()

        boards.forEachIndexed { index, view ->
            view.setOnClickListener {
                play(omokGame, index)
                checkEnded(omokGame)
            }
        }
    }

    private fun printBoard(currentBoard: Board) {
        currentBoard.placedStones.forEach { placedStone ->
            boards[placedStone.point.toIndex()].setImageResource(placedStone.color.toResourceId())
        }
    }

    private fun play(omokGame: OmokGame, clickedIndex: Int) {
        if (omokGame.state is OmokGameState.Running) {
            omokGame.placeStone(::printBoard) { _, _ -> clickedIndex.toPoint() }
            printBoard(omokGame.board)
        }
    }

    private fun checkEnded(omokGame: OmokGame) {
        if (omokGame.state is OmokGameState.End) {
            Toast.makeText(
                this,
                WINNING_MESSAGE.format(
                    omokGame
                        .state
                        .getResult()
                        .toName()
                ),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun Point.toIndex(): Int = (RANGE_UNIT * y) + x

    private fun Int.toPoint(): Point = Point(this % RANGE_UNIT, this / RANGE_UNIT)

    private fun domain.stone.Color.toResourceId(): Int {
        return when (this) {
            is domain.stone.Color.Black -> R.drawable.black_stone
            is domain.stone.Color.White -> R.drawable.white_stone
        }
    }

    private fun domain.stone.Color.toName(): String {
        return when (this) {
            is domain.stone.Color.Black -> "흑"
            is domain.stone.Color.White -> "백"
        }
    }

    companion object {
        private const val WINNING_MESSAGE = "우승자는 %s입니다."
        private const val RANGE_UNIT = 15
    }
}
