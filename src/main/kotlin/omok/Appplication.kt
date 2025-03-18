package omok

import omok.controller.OmokController
import omok.view.InputView
import omok.view.OutputView

fun main() {
    val omokController = OmokController(InputView(), OutputView())
    omokController.run()
}
