package woowacourse.omok.view.output

import android.view.View
import com.google.android.material.snackbar.Snackbar

class AndroidOutputView(private val view: View) : OutputView {
    override fun printTurn(
        stoneColor: String?,
        point: Pair<Int, Int>?,
    ) {
        val (x, y) = point ?: return println("")
        val xAlphabet = intToAlphabet(x - 1)

        Snackbar.make(
            view,
            "${stoneColor}의 차례입니다. (마지막 돌의 위치: ${xAlphabet}$y)",
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

    private fun intToAlphabet(num: Int): Char = (num + 'A'.code).toChar()
}
