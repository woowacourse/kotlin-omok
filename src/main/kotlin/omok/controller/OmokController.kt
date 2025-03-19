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
        playOmok(omokBoard)
        displayWinner(omokBoard)
    }

    private fun playOmok(board: Board) {
        runCatching {
            board.playOmok(
                onTurn = outputView::printTurn,
                onPointInput = inputView::getPoint,
                onBoardUpdated = outputView::printOmokBoard,
            )
        }.getOrElse {
            println(it.message)
            playOmok(board)
        }
    }

    private fun displayWinner(board: Board) {
        if (board.state is Finished) {
            outputView.printWinner((board.state as Finished).winnerColor)
        }
    }
}
