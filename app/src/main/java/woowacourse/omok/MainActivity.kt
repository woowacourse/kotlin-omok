package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.Board
import domain.Color
import domain.OmokGame
import domain.Stone

class MainActivity : AppCompatActivity() {

    private val turnTextView: TextView by lazy { findViewById<TextView>(R.id.turn) }
    private val boardCoordinateViews: List<ImageView> by lazy {
        findViewById<TableLayout>(R.id.board)
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .toList()
    }
    private lateinit var omokGame: OmokGame
    private val omokDbManager: OmokDbManager by lazy {
        OmokDbManager(OmokDbHelper(this))
    }

    private var winner: Color? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        omokGame = omokDbManager.getOmokGame()
        printInitBoard(boardCoordinateViews, omokGame.board)
        showTurn(omokGame.currentColor)

        boardCoordinateViews.forEachIndexed { index, view ->
            view.setOnClickListener {
                val stone = omokGame.getStone { Converter.indexToPosition(index) }
                printStone(view, stone)
                omokDbManager.updateOmokDatabase(stone)
                winner = omokGame.getWinnerColorPhase(stone)
                showWinner(winner)
                winner = reset(boardCoordinateViews, omokGame, winner)
                showTurn(omokGame.currentColor)
            }
        }
    }

    private fun printInitBoard(boardCoordinateViews: List<ImageView>, board: Board) {
        board.stones.values.forEach { stone ->
            val index = Converter.positionToIndex(stone.position)
            val stoneImage = getStoneImage(stone.color)
            boardCoordinateViews[index].setImageResource(stoneImage)
        }
    }

    private fun printStone(view: ImageView, stone: Stone?) {
        if (stone != null) view.setImageResource(getStoneImage(stone.color))
    }

    private fun getStoneImage(color: Color): Int {
        return when (color) {
            Color.BLACK -> R.drawable.black_stone
            Color.WHITE -> R.drawable.white_stone
        }
    }

    private fun showWinner(color: Color?) {
        when (color) {
            Color.BLACK -> {
                showToastMessage(WINNER_MESSAGE.format("흑"))
                omokDbManager.deleteOmokDatabase()
            }
            Color.WHITE -> {
                showToastMessage(WINNER_MESSAGE.format("백"))
                omokDbManager.deleteOmokDatabase()
            }
            else -> {}
        }
    }

    private fun showTurn(color: Color) {
        turnTextView.text = TURN_MESSAGE.format(Converter.colorToString(color))
    }

    private fun reset(allCoordinate: List<ImageView>, omokGame: OmokGame, winner: Color?): Color? {
        if (winner == null) return null
        omokGame.resetGame()
        allCoordinate.forEach { it.setImageResource(0) }
        return null
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val WINNER_MESSAGE = "%s의 승리입니다"
        private const val TURN_MESSAGE = "%s의 차례입니다"
    }
}
