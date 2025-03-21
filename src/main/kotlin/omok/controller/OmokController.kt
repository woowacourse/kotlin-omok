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
        val omokBoard = Board()
        outputView.printStartOmok(omokBoard.size)
        playOmok(omokBoard, omokBoard.size)
        displayWinner(omokBoard)
    }

    private fun playOmok(
        board: Board,
        boardSize: Int,
    ) {
        runCatching {
            board.playOmok(
                onTurn = outputView::printTurn,
                onPointInput = { inputView.getPoint() },
                onBoardUpdated = { black, white -> outputView.printOmokBoard(black, white, boardSize) },
            )
        }.getOrElse {
            println(it.message)
            playOmok(board, boardSize)
        }
    }

    private fun displayWinner(board: Board) {
        if (board.state is Finished) {
            outputView.printWinner((board.state as Finished).winnerColor)
        }
    }
}
