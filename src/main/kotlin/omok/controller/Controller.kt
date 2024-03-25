package omok.controller

import omok.model.BlackRule
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
        var turn: Turn = BlackTurn(BlackRule())

        OutputView.printGameStart()
        OutputView.printBoard(board)

        while (turn !is FinishedTurn) {
            OutputView.printTurn(turn, board.beforePoint)
            val point = getPoint(board, turn)
            turn = board.putStone(point, turn)
            OutputView.printBoard(board)
        }
        OutputView.printTurn(turn, board.beforePoint)
    }

    private fun getPoint(
        board: Board,
        turn: Turn,
    ): Point {
        runCatching {
            val point = InputView.readPoint(board.size)
            if (point in board) {
                OutputView.printInvalidPointInputMessage()
                return getPoint(board, turn)
            }
            return point
        }.onFailure {
            OutputView.printInvalidPointInputMessage()
            OutputView.printTurn(turn, board.beforePoint)
        }
        return getPoint(board, turn)
    }
}
