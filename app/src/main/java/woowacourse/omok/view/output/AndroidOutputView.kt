package woowacourse.omok.view.output

import android.view.View
import com.google.android.material.snackbar.Snackbar
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.entity.StoneColor

class AndroidOutputView(private val view: View) : OutputView {
    override fun printStartGuide() {
        Snackbar.make(view, "오목 게임을 시작합니다", Snackbar.LENGTH_SHORT).show()
    }

    override fun printTurn(
        board: Board,
        stoneColor: StoneColor,
    ) {
        TODO("Not yet implemented")
    }

    override fun printWinner(
        board: Board,
        stoneColor: StoneColor,
    ) {
        TODO("Not yet implemented")
    }

    override fun printInAppropriatePlace(message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }
}
