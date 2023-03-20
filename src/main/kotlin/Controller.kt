package domain

import domain.library.cldhfleks2.Cldhfleks2Rule
import view.OmokView

class Controller {
    fun run() {
        val omokGame = OmokGame(Board(gameRule = Cldhfleks2Rule()))
        OmokView.printStart()
        omokGame.progressTurn(OmokView::putPhase, OmokView::printError)
        OmokView.printResult(omokGame.board, omokGame.turn)
    }
}
