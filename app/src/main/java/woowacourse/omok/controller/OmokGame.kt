package woowacourse.omok.controller

import android.os.Build
import androidx.annotation.RequiresApi
import woowacourse.omok.domain.Board
import woowacourse.omok.library.BudoolRenjuRule
import woowacourse.omok.view.InputView
import woowacourse.omok.view.OutputView

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
