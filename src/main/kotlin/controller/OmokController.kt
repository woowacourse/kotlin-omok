package controller

import domain.OmokGame
import view.InputView
import view.OutputView

class OmokController(private val omokGame: OmokGame = OmokGame()) {

    fun run() {
        runCatching {
            OutputView.printGameStartMessage()
            val winningColor = omokGame.start(OutputView::printOmokBoard, InputView::requestPoint)
            OutputView.printWinner(winningColor)
        }.onFailure { ex ->
            OutputView.printExceptionMessage(ex)
        }
    }
}
