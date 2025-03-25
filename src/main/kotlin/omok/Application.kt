package omok

import omok.controller.OmokController
import omok.view.InputView
import omok.view.OutputView

fun main() {
    val inputView = InputView()
    val outputView = OutputView()
    val controller = OmokController(inputView, outputView)
    controller.run()
}
