package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.* // ktlint-disable no-wildcard-imports

class MainActivity : AppCompatActivity() {

    private val turnTextView: TextView by lazy { findViewById(R.id.turn) }
    private val boardCoordinateViews: List<ImageView> by lazy {
        findViewById<TableLayout>(R.id.board)
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .toList()
    }
    private val omokGame: OmokGame by lazy {
        omokDbManager.getOmokGame(rule = RenjuRuleAdapter())
    }
    private val omokDbManager: OmokDbManager by lazy {
        OmokDbManager(OmokDbHelper(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        printInitBoard(boardCoordinateViews, omokGame.board)
        showTurn(omokGame.currentColor)
        gameStart()
    }

    private fun printInitBoard(boardCoordinateViews: List<ImageView>, board: Board) {
        board.stones.values.forEach { stone ->
            val index = Converter.positionToIndex(stone.position)
            val stoneImage = getStoneImage(stone.color)
            boardCoordinateViews[index].setImageResource(stoneImage)
        }
    }

    private fun printStone(view: ImageView, stone: Stone) {
        view.setImageResource(getStoneImage(stone.color))
    }

    private fun getStoneImage(color: Color): Int {
        return when (color) {
            Color.BLACK -> R.drawable.black_stone
            Color.WHITE -> R.drawable.white_stone
        }
    }

    private fun printWinner(color: Color) {
        when (color) {
            Color.BLACK -> showToastMessage(WINNER_MESSAGE.format(BLACK))
            Color.WHITE -> showToastMessage(WINNER_MESSAGE.format(WHITE))
        }
    }

    private fun showTurn(color: Color) {
        turnTextView.text = TURN_MESSAGE.format(Converter.colorToString(color))
    }

    private fun reset(allCoordinate: List<ImageView>, omokGame: OmokGame) {
        omokGame.resetGame()
        allCoordinate.forEach { it.setImageResource(0) }
        omokDbManager.deleteOmokDatabase()
    }

    private fun showToastMessage(message: String): Unit =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    private fun gameStart(): Unit = boardCoordinateViews.forEachIndexed { index, view ->
        clickBoard(index, view)
    }

    private fun clickBoard(index: Int, view: ImageView) {
        view.setOnClickListener {
            if (omokGame.isRunning()) gameRunning(index, view)
            if (!omokGame.isRunning()) gameFinished()
        }
    }

    private fun gameRunning(index: Int, view: ImageView) {
        val stone = omokGame.getStone(Converter.indexToPosition(index))
        val isSuccess = omokGame.placeTo(stone)
        if (isSuccess) {
            printStone(view, stone)
            omokDbManager.updateOmokDatabase(stone)
            omokGame.checkFinished()
        }
        showTurn(omokGame.currentColor)
    }

    private fun gameFinished() {
        omokGame.getWinnerColor()?.let { printWinner(it) }
        reset(boardCoordinateViews, omokGame)
        showTurn(omokGame.currentColor)
    }

    companion object {
        private const val WINNER_MESSAGE = "%s의 승리입니다"
        private const val TURN_MESSAGE = "%s의 차례입니다"
        private const val BLACK = "흑"
        private const val WHITE = "백"
    }
}
