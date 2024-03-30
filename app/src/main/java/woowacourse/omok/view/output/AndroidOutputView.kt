package woowacourse.omok.view.output

import android.view.View
import com.google.android.material.snackbar.Snackbar
import woowacourse.omok.model.board.Board

class AndroidOutputView(private val view: View) : OutputView {
    override fun printStartGuide() {
        Snackbar.make(view, "오목 게임을 시작합니다", Snackbar.LENGTH_SHORT).show()
    }

    override fun printTurn(board: Board) {
        val previousStone = board.previousStone()
        Snackbar.make(
            view,
            "${previousStone?.stoneColor}의 차례입니다.",
            Snackbar.LENGTH_SHORT,
        ).show()
    }

    override fun printWinner(board: Board) {
        Snackbar.make(
            view,
            "${board.previousStone()?.stoneColor}이 승리했습니다",
            Snackbar.LENGTH_SHORT,
        ).show()
    }

    override fun printInAppropriatePlace(message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }
}
