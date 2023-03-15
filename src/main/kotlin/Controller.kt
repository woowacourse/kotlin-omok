import domain.OmokGame
import view.InputView
import view.OutputView

class Controller {
    fun run() {
        val omokGame = OmokGame()
        OutputView.printStart()
        omokGame.runGame(
            InputView::readPosition,
            OutputView::printOmokState,
            OutputView::printDuplicate,
            OutputView::printWinner
        )
    }
}
