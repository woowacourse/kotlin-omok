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
import domain.OmokGameState
import domain.board.Board
import domain.stone.Color

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
        initClickListener()
    }

    private fun printBoard(currentBoard: Board) {
        currentBoard.placedStones.forEach { placedStone ->
            boards[placedStone.point.toIndex()].setImageResource(placedStone.color.toStoneImage())
        }
    }

    private fun placeStone(clickedIndex: Int) {
        runCatching {
            omokDBAdapter.addStone(clickedIndex, omokGame.turnColor)
            val state = omokGame.placeStone(::printBoard) { _, _ -> clickedIndex.toPoint() }
            printBoard(omokGame.board)
            if (state is OmokGameState.End) {
                getResult(state)
            }
        }.onFailure { ex ->
            ex.message?.let { Log.e(PLACE_STONE_ERROR, it) }
        }
    }

    private fun getResult(state: OmokGameState) {
        runCatching {
            printEndMessage(state.getResult())
            initOmokGames()
        }.onFailure { ex ->
            ex.message?.let { it -> Log.e(RESULT_ERROR, it) }
        }
    }

    private fun initClickListener() {
        boards.forEachIndexed { index, view ->
            view.setOnClickListener {
                placeStone(index)
            }
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

    private fun printEndMessage(winningColor: Color) {
        Toast.makeText(
            this,
            WINNING_MESSAGE.format(
                winningColor.toName()
            ),
            Toast.LENGTH_SHORT
        ).show()
    }

    companion object {
        private const val WINNING_MESSAGE = "우승자는 %s입니다."
        private const val PLACE_STONE_ERROR = "placing stone error"
        private const val RESULT_ERROR = "result error"
    }
}
