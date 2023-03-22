package domain

import domain.library.cldhfleks2.SubdivideRuleAdapter
import view.OmokView

class Controller {
    fun run() {
        val omokGame = OmokGame(gameRule = SubdivideRuleAdapter())
        OmokView.printStart()
        omokGame.progressTurn(OmokView::putPhase, OmokView::printError)
        OmokView.printResult(omokGame.board, omokGame.turn)
    }
}
