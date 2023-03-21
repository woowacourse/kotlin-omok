package domain

import domain.domain.Board2
import view.InputView
import view.OutputView

class Controller {
    fun run() {
        OutputView.printStart()
        val omokGame = OmokGame(Board2())
        val winnerColor = omokGame.getWinnerColor(OutputView::printCurrentState, InputView::inputPosition)
        OutputView.printResult(winnerColor, omokGame.board)
    }
}
