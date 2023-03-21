package domain

import domain.library.cldhfleks2.Cldhfleks2Adapter
import view.OmokView

class Controller {
    fun run() {
        val omokGame = OmokGame(Board(gameRule = Cldhfleks2Adapter()))
        OmokView.printStart()
        omokGame.progressTurn(OmokView::putPhase, OmokView::printError)
        OmokView.printResult(omokGame.board, omokGame.turn)
    }
}
