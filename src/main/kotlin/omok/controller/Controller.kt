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
        OutputView.printTurn(turn)
    }

    private fun getPoint(board: Board): Point {
        runCatching {
            val point = InputView.readPoint()
            if (point in board) {
                throw IllegalArgumentException(MESSAGE_INVALID_POINT_INPUT)
            }
            return point
        }.onFailure {
            println(it.message)
        }
        return getPoint(board)
    }

    companion object {
        private const val MESSAGE_INVALID_POINT_INPUT = "해당 위치 좌표에 이미 돌이 착수되어 있습니다. 다시 입력해주세요."
    }
}
