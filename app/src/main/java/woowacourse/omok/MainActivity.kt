package woowacourse.omok

import android.content.DialogInterface
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import data.DbHelper
import domain.domain.Point
import domain.domain.state.BlackTurn
import domain.domain.state.Finished
import domain.domain.state.Foul
import domain.domain.state.Playing
import domain.domain.state.Ready
import domain.domain.state.State
import domain.domain.state.WhiteTurn
import domain.domain.stone.StoneColor

class MainActivity : AppCompatActivity() {
    private var state: State = Ready()
    private val gameManager = OmokGameManager()
    private lateinit var dbHelper: DbHelper
    private lateinit var boardImages: List<List<ImageView>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DbHelper(this)

        state = dbHelper.loadGameState() ?: Ready()

        val board = findViewById<TableLayout>(R.id.board)
        boardImages =
            board.children
                .filterIsInstance<TableRow>()
                .toList()
                .reversed()
                .map { row -> row.children.filterIsInstance<ImageView>().toList() }

        updateBoard()

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
        val newState = gameManager.updateState(state, point, dbHelper)

        if (newState is Foul) {
            displayFoulMessage(newState)
            state = previousState
            return
        }

        displayStone(imageView)
        state = newState

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
                    DialogInterface.OnClickListener { dialog, id ->
                        dbHelper.clearGameState()
                        dialog.dismiss()
                    },
                )
                .setPositiveButton(
                    R.string.retry,
                    DialogInterface.OnClickListener { dialog, id ->
                        state = Ready()
                        resetBoard()
                    },
                )
                .setCancelable(false)
                .create()
        alertDialog.show()
    }

    private fun resetBoard() {
        boardImages.flatten().forEach { it.setImageResource(0) }
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}
