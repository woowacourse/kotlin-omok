package omok

import omok.controller.OmokController
import omok.view.OmokView

fun main() {
    val omokController = OmokController(OmokView())
    omokController.run()
}
