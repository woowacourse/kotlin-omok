package woowacourse.omok

import omok.view.OutputView
import woowacourse.omok.controller.OmokControl
import woowacourse.omok.view.InputView

fun main() {
    val inputView = InputView()
    val outputView = OutputView()

    OmokControl(inputView, outputView).run()
}
