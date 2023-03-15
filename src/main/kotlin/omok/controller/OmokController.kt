package omok.controller

import omok.model.Board
import omok.model.OmokGame
import omok.view.InputView
import omok.view.OutputView

class OmokController {
    fun run() {
        val board = Board()
        val omokGame = OmokGame(board)
        omokGame.start(InputView::getCoordinate, OutputView::printBoard)
    }
}
