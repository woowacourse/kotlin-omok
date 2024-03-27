package omok.controller

import omok.model.OmokGame
import omok.model.board.Board
import omok.model.stone.BlackStone
import omok.model.stone.WhiteStone
import omok.view.InputView
import omok.view.ProgressView
import omok.view.ResultView

class OmokController(
    private val inputView: InputView,
    private val progressView: ProgressView,
    private val resultView: ResultView,
) {
    fun start() {
        val omokGame = readyOmokGame()

        omokGame.start(
            { inputView.readPosition(it) },
            { progressView.drawBoard(it) },
            { resultView.printWinner(it) },
        )
    }

    private fun readyOmokGame(): OmokGame {
        progressView.printStartGameComment()
        progressView.drawBoard(Board)
        return OmokGame(BlackStone(), WhiteStone())
    }
}
