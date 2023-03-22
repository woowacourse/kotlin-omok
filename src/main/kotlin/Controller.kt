package domain

import domain.library.ark.CombinedRuleAdapter
import view.OmokView

class Controller {
    fun run() {
        val omokGame = OmokGame(gameRule = CombinedRuleAdapter())
        OmokView.printStart()
        omokGame.progressTurn(OmokView::putPhase, OmokView::printError)
        OmokView.printResult(omokGame.board, omokGame.turn)
    }
}
