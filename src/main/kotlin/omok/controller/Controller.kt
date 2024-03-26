package omok.controller

import omok.model.OmokGame
import omok.model.Point
import omok.view.InputView
import omok.view.OutputView

class Controller {
    fun run() {
        OutputView.printGameStart()
        OmokGame().play(OutputView::printBoard, OutputView::printTurn, ::getPoint)
    }

    private fun getPoint(): Point = runCatching { InputView.readPoint() }.getOrNull() ?: getPoint()
}
