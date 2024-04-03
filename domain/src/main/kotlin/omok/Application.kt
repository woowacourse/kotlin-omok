package omok

import omok.controller.OmokController
import omok.view.InputView
import omok.view.ProgressView

fun main() {
    val controller =
        OmokController(
            InputView(),
            ProgressView(),
        )
    controller.start()
}
