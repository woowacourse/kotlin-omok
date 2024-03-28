package omok.controller

import omok.model.OmokGame
import omok.view.InputView
import omok.view.OutputView

class OmokController(private val inputView: InputView, private val outputView: OutputView) {
    fun start() {
        val omokGame = readyOmokGame()

        omokGame.start(
            { inputView.readPosition(it) },
            { outputView.drawBoard() },
            { outputView.printWinner(it) },
        )
    }

    private fun readyOmokGame(): OmokGame {
        outputView.printStartGameComment()
        outputView.drawBoard()
        return OmokGame()
    }
}
