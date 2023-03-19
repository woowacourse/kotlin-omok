package omok.controller

import omok.domain.OmokGame
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val inputView: InputView = InputView(),
    private val outputView: OutputView = OutputView(),
) {
    fun run() {
        outputView.outputInit()
        OmokGame().play(inputView::inputPoint, outputView::outputBoard)
    }
}
