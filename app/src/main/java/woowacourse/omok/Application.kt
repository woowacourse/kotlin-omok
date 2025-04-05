package woowacourse.omok

import InputView
import OmokController
import OutputView

fun main() {
    val inputView = InputView()
    val outputView = OutputView()
    val controller = OmokController(inputView, outputView)
    controller.run()
}
