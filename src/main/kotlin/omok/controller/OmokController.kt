package omok.controller

import omok.model.BlackStone
import omok.model.Board
import omok.model.OmokGame
import omok.model.WhiteStone
import omok.view.InputView
import omok.view.OutputView

class OmokController(private val inputView: InputView, private val outputView: OutputView) {
    fun start() {
        val omokGame = readyOmokGame()

        omokGame.startGame(
            { inputView.readPosition(it) },
            { outputView.drawBoard(it) },
        )
    }

    private fun readyOmokGame(): OmokGame {
        outputView.printStartGameComment()
        outputView.drawBoard(Board)
        return OmokGame(BlackStone(), WhiteStone())
    }
}
