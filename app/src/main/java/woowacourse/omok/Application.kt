package woowacourse.omok

import woowacourse.omok.domain.controller.OmokController
import woowacourse.omok.view.InputView
import woowacourse.omok.view.OutputView

fun main() {
    val inputView = InputView()
    val outputView = OutputView()
    val controller = OmokController(inputView, outputView)
    controller.run()
}
