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
import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.state.BlackTurn
import woowacourse.omok.domain.model.state.Finished
import woowacourse.omok.domain.model.state.Foul
import woowacourse.omok.domain.model.state.Playing
import woowacourse.omok.domain.model.state.Ready
import woowacourse.omok.domain.model.state.State
import woowacourse.omok.domain.model.state.WhiteTurn
import woowacourse.omok.domain.model.stone.StoneColor

class MainActivity : AppCompatActivity() {
    private var state: State = Ready()
    private val gameManager = OmokGameManager()
    private lateinit var omokDao: OmokDao
    private lateinit var boardImages: Sequence<Sequence<ImageView>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHelper = DbHelper(this)
        omokDao = OmokDao(dbHelper)

        state = omokDao.loadGameState() ?: Ready()

        val board = findViewById<TableLayout>(R.id.board)
        getBoardImages(board)

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
        if (state !is Playing) return

        val previousState = state
        val newState = gameManager.updateState(state, point, omokDao)

        if (newState is Foul) {
            handleFoul(previousState, newState)
            return
        }

        updateStateAndDisplayStone(newState, imageView)
        handleWin(newState)
    }

    private fun handleFoul(
        previousState: State,
        foul: Foul,
    ) {
        displayFoulMessage(foul)
        state = previousState
    }

    private fun updateStateAndDisplayStone(
        newState: State,
        imageView: ImageView,
    ) {
        displayStone(imageView)
        state = newState
    }

    private fun handleWin(newState: State) {
        if (newState is Finished.Win) {
            displayWinner(newState.winnerColor)
            showGameOverBox()
        }
    }

    private fun displayStone(imageView: ImageView) {
        imageView.setImageResource(
            when (state) {
                is BlackTurn -> R.drawable.black_stone
                is WhiteTurn -> R.drawable.white_stone
                else -> R.drawable.black_stone
            },
        )
    }

    private fun displayWinner(stoneColor: StoneColor) {
        val messageId =
            if (stoneColor == StoneColor.BLACK) R.string.black_win else R.string.white_win
        Toast.makeText(applicationContext, messageId, Toast.LENGTH_LONG).show()
    }

    private fun displayFoulMessage(state: State) {
        val messageId =
            when (state) {
                Foul.DoubleThree -> R.string.double_three
                Foul.DoubleFour -> R.string.double_four
                Foul.OverLine -> R.string.over_line
                Foul.Duplicated -> R.string.duplicated
                else -> return
            }
        Toast.makeText(applicationContext, messageId, Toast.LENGTH_LONG).show()
    }

    private fun updateBoard() {
        if (state !is Playing) return

        val currentState = state
        if (currentState is Playing) {
            boardImages.forEachIndexed { rowIndex, row ->
                row.forEachIndexed { colIndex, imageView ->
                    val point = Point(colIndex, rowIndex)
                    imageView.setImageResource(getStoneForPoint(currentState, point))
                }
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
                .setNegativeButton(
                    R.string.game_over,
                ) { dialog, id ->
                    omokDao.clearGameState()
                    dialog.dismiss()
                }
                .setPositiveButton(
                    R.string.retry,
                ) { dialog, id ->
                    omokDao.clearGameState()
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
