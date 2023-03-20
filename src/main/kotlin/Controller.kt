package domain

import view.OmokView

class Controller {
    fun run() {
        val omokGame = OmokGame(Board())
        OmokView.printStart()
        omokGame.progressTurn(OmokView::putPhase, OmokView::printError)
        OmokView.printResult(omokGame.board, omokGame.turn)
    }
}
