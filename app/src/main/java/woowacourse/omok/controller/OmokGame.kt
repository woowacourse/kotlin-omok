package woowacourse.omok.controller

import android.os.Build
import androidx.annotation.RequiresApi
import woowacourse.omok.domain.Board
import woowacourse.omok.domain.Position
import woowacourse.omok.domain.StoneType
import woowacourse.omok.domain.Turn
import woowacourse.omok.library.BudoolRenjuRule
import woowacourse.omok.view.InputView
import woowacourse.omok.view.OutputView

class OmokGame(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    val board = Board.initial()
    val turn = Turn()
    val rule = BudoolRenjuRule()

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun start() {
        outputView.printStartMessage()
        var lastPosition: Position? = null

        while (true) {
            outputView.drawBoard(board)
            outputView.printTurn(turn.currentPlayer, lastPosition)

            val position = inputView.readPosition()
            try {
                board.put(position, turn.currentPlayer)

                if (rule.checkWin(board, position)) {
                    outputView.drawBoard(board)
                    outputView.showWin(turn.currentPlayer)
                    return
                }

                if (turn.currentPlayer == StoneType.BLACK) {
                    if (rule.checkDoubleThreeFoul(board, position)) {
                        throw IllegalStateException("삼삼 금수입니다.")
                    }
                    if (rule.checkDoubleFourFoul(board, position)) {
                        throw IllegalStateException("사사 금수입니다.")
                    }
                    if (rule.checkOverline(board, position)) {
                        throw IllegalStateException("장목 금수입니다.")
                    }
                }

                lastPosition = position
                turn.switch()
            } catch (e: Exception) {
                outputView.showError(e.message ?: "알 수 없는 오류가 발생했습니다.")
            }
        }
    }
}
