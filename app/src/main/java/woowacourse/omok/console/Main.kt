package woowacourse.omok.console

import woowacourse.omok.console.controller.OmokGame
import woowacourse.omok.console.view.InputView
import woowacourse.omok.console.view.OutputView

fun main() {
    val inputView = InputView()
    val outputView = OutputView()
    val omokGame = OmokGame(inputView, outputView)
    omokGame.start()
}
