package omok.controller

import omok.model.OmokGame
import omok.model.board.Board
import omok.model.stone.BlackStone
import omok.view.InputView
import omok.view.ProgressView

class OmokController(
    private val inputView: InputView,
    private val progressView: ProgressView,
) {
    fun start() {
        val omokGame = readyOmokGame()

        omokGame.start(
            { inputView.readPosition(it) },
            { progressView.drawBoard(it) },
            { progressView.printHintMessage(it) },
        )
    }

    private fun readyOmokGame(): OmokGame {
        progressView.printStartGameComment()
        progressView.drawBoard(Board)
        return OmokGame(BlackStone())
    }
}
