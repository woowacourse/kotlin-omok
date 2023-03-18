package controller

import domain.OmokGame
import view.InputView
import view.OutputView

class OmokController(
    private val omokGame: OmokGame = OmokGame()
) {

    fun run() {
        OutputView.printGameStartMessage()
        val winningColor = omokGame.start(OutputView::printOmokBoardState, InputView::requestPoint)
        OutputView.printWinner(winningColor)
    }
}
