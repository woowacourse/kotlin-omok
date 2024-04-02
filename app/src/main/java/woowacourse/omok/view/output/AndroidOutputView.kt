package woowacourse.omok.view.output

import android.view.View
import com.google.android.material.snackbar.Snackbar
import woowacourse.omok.model.board.Board

class AndroidOutputView(private val view: View) : OutputView {
    override fun printTurn(board: Board) {
        val previousStone = board.previousStone()
        Snackbar.make(
            view,
            "${previousStone?.stoneColor}의 차례입니다.",
            Snackbar.LENGTH_SHORT,
        ).show()
    }

    override fun printWinner(stoneColor: String?) {
        Snackbar.make(
            view,
            "${stoneColor}이 승리했습니다",
            Snackbar.LENGTH_SHORT,
        ).show()
    }

    override fun printAlert(message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }
}
