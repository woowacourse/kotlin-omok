import domain.Board
import domain.OmokGame
import view.InputView
import view.OutputView

class Controller {
    fun run() {
        OutputView.printStart()
        val omokGame = OmokGame(Board())
        val winnerColor = omokGame.getWinnerColor(OutputView::printCurrentState, InputView::inputPosition)
        OutputView.printResult(winnerColor, omokGame.board)
    }
}
