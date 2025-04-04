package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.data.DbHelper
import woowacourse.omok.data.OmokDao
import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.state.State
import woowacourse.omok.domain.model.state.State.Finished
import woowacourse.omok.domain.model.state.State.Foul
import woowacourse.omok.domain.model.state.State.Playing
import woowacourse.omok.domain.model.stone.StoneColor

class MainActivity : AppCompatActivity() {
    private lateinit var board: Board
    private val gameManager = OmokGameManager()
    private lateinit var omokDao: OmokDao
    private lateinit var boardImages: Sequence<Sequence<ImageView>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHelper = DbHelper(this)
        omokDao = OmokDao(dbHelper)

        val savedState = omokDao.loadGameState()
        board = if (savedState != null) Board(savedState) else Board()

        val boardView = findViewById<TableLayout>(R.id.board)
        getBoardImages(boardView)

        updateBoard()
        setupImageViewClickListeners()
    }

    private fun getBoardImages(board: TableLayout) {
        boardImages =
            board.children
                .filterIsInstance<TableRow>()
                .map { row -> row.children.filterIsInstance<ImageView>() }
    }

    private fun setupImageViewClickListeners() {
        boardImages.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, imageView ->
                imageView.setOnClickListener { placeStone(Point(colIndex, rowIndex), imageView) }
            }
        }
    }

    private fun placeStone(
        point: Point,
        imageView: ImageView,
    ) {
        if (board.state !is Playing) return

        val previousState = board.state
        val newState = gameManager.updateState(board, point, omokDao)

        if (newState is Foul) {
            handleFoul(previousState, newState)
            return
        }

        if (previousState is Playing) updateStateAndDisplayStone(previousState.nextStoneColor, newState, imageView)
        handleWin(newState)
    }

    private fun handleFoul(
        previousState: State,
        foul: Foul,
    ) {
        displayFoulMessage(foul)
        board.state = previousState
    }

    private fun updateStateAndDisplayStone(
        placedStoneColor: StoneColor,
        newState: State,
        imageView: ImageView,
    ) {
        displayStone(imageView, placedStoneColor)
        board.state = newState
    }

    private fun handleWin(newState: State) {
        if (newState is Finished) {
            displayWinner(newState.winnerColor)
            showGameOverBox()
        }
    }

    private fun displayStone(
        imageView: ImageView,
        stoneColor: StoneColor,
    ) {
        imageView.setImageResource(
            when (stoneColor) {
                StoneColor.BLACK -> R.drawable.black_stone
                StoneColor.WHITE -> R.drawable.white_stone
            },
        )
    }

    private fun displayWinner(stoneColor: StoneColor?) {
        val messageId =
            if (stoneColor == StoneColor.BLACK) R.string.black_win else R.string.white_win
        Toast.makeText(applicationContext, messageId, Toast.LENGTH_LONG).show()
    }

    private fun displayFoulMessage(foul: Foul) {
        val messageId =
            when (foul) {
                Foul.DoubleThree -> R.string.double_three
                Foul.DoubleFour -> R.string.double_four
                Foul.OverLine -> R.string.over_line
                Foul.Duplicated -> R.string.duplicated
            }
        Toast.makeText(applicationContext, messageId, Toast.LENGTH_LONG).show()
    }

    private fun updateBoard() {
        if (board.state !is Playing) return

        val currentState = board.state as Playing
        boardImages.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, imageView ->
                val point = Point(colIndex, rowIndex)
                imageView.setImageResource(getStoneForPoint(currentState, point))
            }
        }
    }

    private fun getStoneForPoint(
        currentState: Playing,
        point: Point,
    ): Int {
        return when (point) {
            in currentState.blackStones.points -> R.drawable.black_stone
            in currentState.whiteStones.points -> R.drawable.white_stone
            else -> 0
        }
    }

    fun showGameOverBox() {
        val alertDialog =
            AlertDialog.Builder(this)
                .setTitle(R.string.game_over)
                .setMessage(R.string.game_retry_message)
                .setNegativeButton(R.string.game_over) { dialog, _ ->
                    omokDao.clearGameState()
                    dialog.dismiss()
                }
                .setPositiveButton(R.string.retry) { dialog, _ ->
                    omokDao.clearGameState()
                    board = Board()
                    resetBoard()
                }
                .setCancelable(false)
                .create()
        alertDialog.show()
    }

    private fun resetBoard() {
        boardImages.flatten().forEach { it.setImageResource(0) }
    }
}
