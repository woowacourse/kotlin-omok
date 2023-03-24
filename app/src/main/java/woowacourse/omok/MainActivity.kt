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

    private val boards: List<ImageView> by lazy {
        findViewById<TableLayout>(R.id.board)
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .toList()
    }
    private lateinit var omokGame: OmokGame
    private val omokDBAdapter: OmokDBAdapter by lazy {
        OmokDBAdapter(OmokDBHelper(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
            boards[placedStone.point.toIndex()].setImageResource(placedStone.color.toStoneImage())
        }
    }

    private fun placeStone(clickedIndex: Int) {
        if (omokGame.state is OmokGameState.Running) {
            omokDBAdapter.addStone(clickedIndex, omokGame.turnColor)
            omokGame.placeStone(::printBoard) { _, _ -> clickedIndex.toPoint() }
            printBoard(omokGame.board)
        }
    }

    private fun clearBoard() {
        boards.forEach { board ->
            board.setImageBitmap(null)
        }
    }

    private fun initOmokGames() {
        omokDBAdapter.deleteStones()
        omokGame = OmokGame()
        clearBoard()
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
            initOmokGames()
        }
    }

    companion object {
        private const val WINNING_MESSAGE = "우승자는 %s입니다."
    }
}
