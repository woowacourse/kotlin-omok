package omok.controller

import android.os.Build
import androidx.annotation.RequiresApi
import omok.domain.Board
import omok.library.BudoolRenjuRule
import omok.view.InputView
import omok.view.OutputView

class OmokGame(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun start() {
        val board = Board.initial()
        val rule = BudoolRenjuRule()

        val current = board.currentTurn
        outputView.print(board.currentTurn)
        val position = inputView.readPosition()

        if (rule.checkWin(board, position)) {
        }
        board.put(position, current)
    }
}
