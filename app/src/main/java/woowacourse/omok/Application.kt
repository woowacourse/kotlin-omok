package omok

import omok.controller.OmokControl
import omok.view.InputView
import omok.view.OutputView

fun main() {
    val inputView = InputView()
    val outputView = OutputView()

    OmokControl(inputView, outputView).run()
}
