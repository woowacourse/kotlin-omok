package omok

import omok.controller.OmokController
import omok.view.input.ConsoleInputView
import omok.view.output.ConsoleOutputView

fun main() {
    val inputView = ConsoleInputView()
    val outputView = ConsoleOutputView()
    val omokController = OmokController(inputView, outputView)
    omokController.startOmok()
}
