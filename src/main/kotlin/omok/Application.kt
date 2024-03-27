package omok

import omok.controller.OmokController
import omok.view.InputView
import omok.view.ProgressView
import omok.view.ResultView

fun main() {
    val controller =
        OmokController(
            InputView(),
            ProgressView(),
            ResultView(),
        )
    controller.start()
}
