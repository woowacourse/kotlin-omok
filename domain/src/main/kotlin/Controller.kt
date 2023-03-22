import domain.Board
import domain.OmokGame
import domain.RenjuRuleAdapter
import view.InputView
import view.OutputView

class Controller {
    fun run() {
        OutputView.printStart()
        val omokGame = OmokGame(Board(rule = RenjuRuleAdapter()))
        val winnerColor =
            omokGame.getWinnerColorPhase(OutputView::printCurrentState, InputView::inputPosition)
        OutputView.printResult(winnerColor, omokGame.board)
    }
}
