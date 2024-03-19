package omok.controller

import omok.model.Board
import omok.model.Position
import omok.view.BoardView
import omok.view.InputView
import omok.view.OutputView

class Controller {
    fun play() {
        val board = Board()
        printStart(board)

        val position = readPosition()
    }

    private fun readPosition(): Position {
        return InputView.readPosition()
    }

    private fun printStart(board: Board) {
        OutputView.printStartHeader()
        BoardView.printBoard(board)
    }
}
