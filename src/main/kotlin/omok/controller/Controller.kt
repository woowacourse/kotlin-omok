package omok.controller

import omok.model.OmokGame
import omok.view.InputView
import omok.view.OutputView

class Controller {
    fun run() {
        OutputView.printGameStart()
        OmokGame().play(OutputView::printBoard, OutputView::printTurn, InputView::readPoint)
    }
}
