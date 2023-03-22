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

class MainActivity : AppCompatActivity() {

    private lateinit var boards: List<ImageView>
    private lateinit var omokAdapter: OmokDBAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        boards = findViewById<TableLayout>(R.id.board)
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .toList()
        omokAdapter = OmokDBAdapter(OmokDBHelper(this))

        val omokGame = OmokGame(omokAdapter.getStones())
        printBoard(omokGame.board)

        boards.forEachIndexed { index, view ->
            view.setOnClickListener {
                placeStone(omokGame, index)
            }
        }
    }

    private fun printBoard(currentBoard: Board) {
        currentBoard.placedStones.forEach { placedStone ->
            boards[placedStone.point.toIndex()].setImageResource(placedStone.color.toResourceId())
        }
    }

    private fun placeStone(omokGame: OmokGame, clickedIndex: Int) {
        if (omokGame.state is OmokGameState.Running) {
            omokAdapter.addStone(clickedIndex, omokGame.turnColor)
            omokGame.placeStone(::printBoard) { _, _ -> clickedIndex.toPoint() }
            printBoard(omokGame.board)
            return
        }
        printEnded(omokGame)
    }

    private fun printEnded(omokGame: OmokGame) {
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
            omokAdapter.deleteStones()
        }
    }

    companion object {
        private const val WINNING_MESSAGE = "우승자는 %s입니다."
    }
}
