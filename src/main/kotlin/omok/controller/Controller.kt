package omok.controller

import omok.model.BlackTurn
import omok.model.Board
import omok.model.FinishedTurn
import omok.model.Turn
import omok.view.InputView
import omok.view.OutputView

class Controller {
    fun play() {
        val board = Board()
        OutputView.printBoard(board)
        var turn: Turn = BlackTurn()

        while (turn !is FinishedTurn) {
            val point = InputView.readPoint()
            turn = turn.putStone(point, board)
            OutputView.printBoard(board)
        }
    }
}
