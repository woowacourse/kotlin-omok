package omok

import omok.controller.OmokController
import omok.model.OmokGame
import omok.view.input.ConsoleInputView
import omok.view.output.ConsoleOutputView

fun main() {
    val inputView = ConsoleInputView()
    val outputView = ConsoleOutputView()
    val omokGame = OmokGame()
    val omokController = OmokController(inputView, outputView, omokGame)
    omokController.startOmok()
}
