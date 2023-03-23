import domain.Board
import domain.OmokGame
import domain.RenjuRuleAdapter
import view.InputView
import view.OutputView

class Controller {
    fun run() {
        OutputView.printStart()
        val omokGame = OmokGame(Board(rule = RenjuRuleAdapter()))
        OutputView.printCurrentState(omokGame)
        var winnerColor =
            omokGame.getWinnerColorPhase(OutputView::printCurrentState, InputView::inputPosition)
        while (winnerColor == null) {
            winnerColor = omokGame.getWinnerColorPhase(
                OutputView::printCurrentState,
                InputView::inputPosition
            )
        }
        if (winnerColor != null) {
            OutputView.printResult(winnerColor, omokGame.board)
        }
    }
}
