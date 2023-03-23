package woowacourse.omok

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
import domain.stone.Color
import domain.stone.Point

class MainActivity : AppCompatActivity() {

    private lateinit var boards: List<ImageView>
    private lateinit var omokDBAdapter: OmokDBAdapter
    private lateinit var omokGame: OmokGame

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        boards = findViewById<TableLayout>(R.id.board)
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .toList()
        omokDBAdapter = OmokDBAdapter(OmokDBHelper(this))
        omokGame = OmokGame(omokDBAdapter.getStones())

        printBoard(omokGame.board)

        boards.forEachIndexed { index, view ->
            view.setOnClickListener {
                placeStone(index)
                checkEnded()
            }
        }
    }

    private fun printBoard(currentBoard: Board) {
        omokDBAdapter.getStones()
        currentBoard.placedStones.forEach { placedStone ->
            boards[placedStone.point.toIndex()].setImageResource(placedStone.color.toResourceId())
        }
    }

    private fun placeStone(clickedIndex: Int) {
        if (omokGame.state is OmokGameState.Running) {
            omokDBAdapter.addStone(clickedIndex, omokGame.turnColor)
            omokGame.placeStone(::printBoard) { _, _ -> clickedIndex.toPoint() }
            printBoard(omokGame.board)
        }
    }

    private fun checkEnded() {
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
            omokDBAdapter.deleteStones()
            omokGame = OmokGame()
            clearBoard()
        }
    }

    private fun clearBoard() {
        boards.forEach { board ->
            board.setImageBitmap(null)
        }
    }

    private fun Color.toResourceId(): Int {
        return when (this) {
            is Color.Black -> R.drawable.custom_black_stone
            is Color.White -> R.drawable.custom_white_stone
        }
    }

    private fun Point.toIndex(): Int = (RANGE_UNIT * (y - 1)) + (x - 1)

    companion object {
        private const val WINNING_MESSAGE = "우승자는 %s입니다."
    }
}
