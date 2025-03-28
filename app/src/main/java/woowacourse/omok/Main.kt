package woowacourse.omok

import woowacourse.omok.controller.OmokGame
import woowacourse.omok.view.InputView
import woowacourse.omok.view.OutputView

fun main() {
    val inputView = InputView()
    val outputView = OutputView()
    val omokGame = OmokGame(inputView, outputView)
    omokGame.start()
}
