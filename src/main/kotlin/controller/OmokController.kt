package controller

import domain.OmokGame
import view.InputView
import view.OutputView

class OmokController(
    private val inputView: InputView = InputView(),
    private val outputView: OutputView = OutputView()
) : Runnable {
    override fun run() {
        outputView.printGameStartMessage()
        val omokGame = OmokGame()
        val result = omokGame.start(outputView::printOmokBoardState, inputView::requestPoint2)
    }
}
