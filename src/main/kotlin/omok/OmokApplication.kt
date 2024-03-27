package omok

import omok.controller.OmokController
import omok.view.InputView
import omok.view.OutputView

fun main() {
    OmokController(
        inputView = InputView(),
        outputView = OutputView(),
    ).start()
}
