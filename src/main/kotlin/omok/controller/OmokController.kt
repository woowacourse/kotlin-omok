package omok.controller

import omok.domain.OmokBoard
import omok.domain.state.Finished
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun run() {
        val board = OmokBoard()
        outputView.printStartOmok(board.size)
        playOmok(board, board.size)
        displayWinner(board)
    }

    private fun playOmok(
        board: OmokBoard,
        boardSize: Int,
    ) {
        runCatching {
            board.play(
                onTurn = outputView::printTurn,
                onPointSelected = inputView::getPoint,
                onBoardUpdated = { black, white -> outputView.printOmokBoard(black, white, boardSize) },
            )
        }.getOrElse {
            println(it.message)
            playOmok(board, boardSize)
        }
    }

    private fun displayWinner(board: OmokBoard) {
        if (board.state is Finished) {
            outputView.printWinner((board.state as Finished).winnerColor)
        }
    }
}
