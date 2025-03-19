package omok.controller

import omok.domain.Board
import omok.domain.state.Finished
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun run() {
        outputView.printStartOmok()

        val omokBoard = Board()
        omokBoard.playOmok(
            onTurn = outputView::printTurn,
            onPointInput = inputView::getPoint,
            onBoardUpdated = outputView::printOmokBoard,
        )

        if (omokBoard.state is Finished) {
            outputView.printWinner((omokBoard.state as Finished).winnerColor)
        }
    }
}
