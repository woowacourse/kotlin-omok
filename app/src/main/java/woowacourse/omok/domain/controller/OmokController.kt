package woowacourse.omok.domain.controller

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.state.Finished
import woowacourse.omok.view.InputView
import woowacourse.omok.view.OutputView

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
                onPointInput = { inputView.getPoint(boardSize) },
                onBoardUpdated = { black, white -> outputView.printOmokBoard(black.points, white.points, boardSize) },
            )
        }.getOrElse {
            println(it.message)
            playOmok(board, boardSize)
        }
    }

    private fun displayWinner(board: Board) {
        val currentState = board.state

        if (currentState is Finished.Win) {
            outputView.printWinner(currentState.winnerColor)
        } else {
            outputView.printWinner(null)
        }
    }
}
