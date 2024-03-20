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
        OutputView.printGameStart()
        OutputView.printBoard(board)
        var turn: Turn = BlackTurn()

        while (turn !is FinishedTurn) {
            OutputView.printTurn(turn)
            val point = getPoint(board)
            turn = turn.putStone(point, board)
            OutputView.printBoard(board)
        }
    }

    private fun getPoint(board: Board): Point {
        val point = InputView.readPoint()
        return if (point in board) {
            OutputView.printDuplicatedPointMessage()
            getPoint(board)
        } else {
            point
        }
    }
}
