package woowacourse.omok

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.google.android.material.snackbar.Snackbar
import omock.model.Column
import omock.model.Column.Companion.toColumn
import omock.model.OMockGame
import omock.model.Row
import omock.model.Row.Companion.toRow
import omock.model.state.Stone
import omock.model.turn.BlackTurn
import omock.model.turn.FinishedTurn
import omock.model.turn.Turn
import omock.model.turn.WhiteTurn
import omock.view.OutputView

class MainActivity : AppCompatActivity() {
    private val oMockGame = OMockGame()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        OutputView.outputGameStart()
        setupBoardClickListener()
    }

    private fun setupBoardClickListener() {
        val board = findViewById<TableLayout>(R.id.board)
        val size = board.children.toMutableList().size

        board.children.filterIsInstance<TableRow>().flatMap { it.children }
            .filterIsInstance<ImageView>().forEachIndexed { idx, view ->
                view.setOnClickListener {
                    handleStonePlacement(idx, size, view)
                }
            }
    }

    private fun handleStonePlacement(
        idx: Int,
        size: Int,
        view: ImageView,
    ) {
        val turn = oMockGame.getTurn()
        turn.toStoneIconRes()?.let { stoneIconRes ->
            val rowIndex = idx / size
            val columnIndex = idx % size

            if (executeTurn(rowIndex.toRow(size), columnIndex.toColumn())) {
                view.setImageResource(stoneIconRes)
                handleTurnCompletion(view)
            }
        }
    }

    private fun executeTurn(
        row: Row,
        column: Column,
    ): Boolean {
        var isSuccess = true

        oMockGame.playGame({ turn ->
            displayTurnInfo(turn)
            Stone(row, column)
        }) { e ->
            isSuccess = false
            OutputView.outputErrorMessage(e)
        }

        OutputView.outputBoard()

        return isSuccess
    }

    private fun displayTurnInfo(turn: Turn) {
        OutputView.outputUserTurn(Stone.getStoneName(turn))
        turn.stoneHistory.lastOrNull()?.let { stone ->
            OutputView.outputLastStone(stone)
        } ?: OutputView.outputPrintLine()
    }

    private fun handleTurnCompletion(view: View) {
        if (oMockGame.getTurn() is FinishedTurn) {
            showSuccessSnackbar(view)
            OutputView.outputSuccessOMock()
        }
    }

    private fun showSuccessSnackbar(view: View) {
        Snackbar.make(view, getString(R.string.success_omock), Snackbar.LENGTH_SHORT).show()
    }

    private fun Turn.toStoneIconRes(): Int? {
        return when (this) {
            is BlackTurn -> R.drawable.black_stone
            is WhiteTurn -> R.drawable.white_stone
            else -> null
        }
    }
}
