package woowacourse.omok.presentation

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.view.children
import omok.model.Column
import omok.model.Column.Companion.toColumnComma
import omok.model.OMokBoard
import omok.model.OMokGame
import omok.model.Row
import omok.model.Row.Companion.toRowComma
import omok.model.state.Stone
import omok.model.turn.FinishedTurn
import omok.model.turn.Turn
import omok.view.OutputView
import woowacourse.omok.R
import woowacourse.omok.local.Omok
import woowacourse.omok.local.OmokDbHelper

class MainActivity : OmokGameActivity(R.layout.activity_main) {
    override lateinit var db: OmokDbHelper
    override var oMokGame = OMokGame()

    override fun initStartView() {
        initOmok()
        resetOmok()
        OutputView.outputGameStart()
        setupBoardClickListener()
    }

    override fun initOmok() {
        val board = findViewById<TableLayout>(R.id.board)
        val size = board.children.toMutableList().size
        val omoks = db.selectOmok()

        omoks.forEach { omok ->
            val row = Row(omok.rowComma)
            val column = Column(omok.columnComma)

            val view =
                board.children.filterIsInstance<TableRow>().flatMap { it.children }
                    .filterIsInstance<ImageView>()
                    .toList()[((row.toIntIndex() - 1) * size) + column.getIndex()]

            val turn = oMokGame.getTurn()

            turn.toStoneIconRes()?.let { stoneIconRes ->
                if (executeTurn(row, column)) {
                    view.setImageResource(stoneIconRes)
                    handleTurnCompletion(view)
                }
            }
        }
    }

    override fun resetOmok() {
        val resetBtn = findViewById<Button>(R.id.btn_reset)
        resetBtn.setOnClickListener {
            db.deleteAllOmok()
            oMokGame = OMokGame()

            val board = findViewById<TableLayout>(R.id.board)
            board.children.filterIsInstance<TableRow>().flatMap { it.children }
                .filterIsInstance<ImageView>().forEach { view ->
                    view.setImageResource(0)
                }

            OMokBoard.resetBoard()
            OutputView.outputBoard()
        }
    }

    override fun setupBoardClickListener() {
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
        val turn = oMokGame.getTurn()
        turn.toStoneIconRes()?.let { stoneIconRes ->
            val rowIndex = idx / size
            val columnIndex = idx % size

            val rowComma = rowIndex.toRowComma(size)
            val columnComma = columnIndex.toColumnComma()

            if (executeTurn(Row(rowComma), Column(columnComma))) {
                view.setImageResource(stoneIconRes)
                handleTurnCompletion(view)

                val omok = Omok(rowComma = rowComma, columnComma = columnComma)
                db.insertOmok(omok)
            }
        } ?: showSuccessSnackbar(view, getString(R.string.finished_omock))
    }

    private fun executeTurn(
        row: Row,
        column: Column,
    ): Boolean {
        var isSuccess = true

        oMokGame.playGame({ turn ->
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
        if (oMokGame.getTurn() is FinishedTurn) {
            showSuccessSnackbar(view, getString(R.string.success_omock))
            OutputView.outputSuccessOMock()
        }
    }
}
