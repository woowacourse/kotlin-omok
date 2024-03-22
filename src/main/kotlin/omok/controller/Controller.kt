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
        val board = Board()
        var turn: Turn = BlackTurn()

        OutputView.printGameStart()
        OutputView.printBoard(board)

        while (turn !is FinishedTurn) {
            OutputView.printTurn(turn)
            val point = getPoint(board, turn)
            turn = board.putStone(point)
            OutputView.printBoard(board)
        }
        OutputView.printTurn(turn)
    }

    private fun getPoint(
        board: Board,
        turn: Turn,
    ): Point {
        runCatching {
            val point = InputView.readPoint()
            if (point in board) {
                throw IllegalArgumentException()
            }
            return point
        }.onFailure {
            OutputView.printPointInputErrorMessage()
            OutputView.printTurn(turn)
        }
        return getPoint(board, turn)
    }
}
