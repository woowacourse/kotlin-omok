package omok.controller

import omok.model.BlackTurn
import omok.model.Board
import omok.model.FinishedTurn
import omok.model.Point
import omok.model.Turn
import omok.view.InputView
import omok.view.OutputView

class Controller {
    fun play() {
        val board = Board(15)
        var turn: Turn = BlackTurn()

        OutputView.printGameStart()
        OutputView.printBoard(board)

        while (turn !is FinishedTurn) {
            OutputView.printTurn(turn, board.beforePoint)
            turn = board.putStone(readPoint(board, turn), turn)
            OutputView.printBoard(board)
        }
        OutputView.printTurn(turn, board.beforePoint)
    }

    private fun readPoint(
        board: Board,
        turn: Turn,
    ): Point {
        runCatching {
            return InputView.readPoint(board)
        }.onFailure {
            OutputView.printInvalidPointInputMessage()
            OutputView.printTurn(turn, board.beforePoint)
        }
        return readPoint(board, turn)
    }
}
