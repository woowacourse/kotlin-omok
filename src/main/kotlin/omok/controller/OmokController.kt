package omok.controller

import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun run() {
        outputView.printStartOmok()
        outputView.printFirstTurn()
        val point = inputView.getPoint()
    }
}
