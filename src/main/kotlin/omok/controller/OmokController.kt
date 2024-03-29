package omok.controller

import omok.model.OmokGame
import omok.view.input.InputView
import omok.view.output.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
    private val omokGame: OmokGame,
) {
    fun startOmok() {
        outputView.printStartGuide()
        omokGame.justPrintBoard(
            outputView::printTurn,
            outputView::printError,
        )
        while (!omokGame.isFinished()) {
            runOmok()
        }
        runOmok()
    }

    private fun runOmok() {
        omokGame.run(
            inputView::getStonePoint,
            outputView::printTurn,
            outputView::printError,
            outputView::printWinner,
        )
    }
}
