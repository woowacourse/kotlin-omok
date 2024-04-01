package woowacourse.omok.domain.controller

import woowacourse.omok.domain.model.BlackTurn
import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.FinishedTurn
import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.RuleAdapter
import woowacourse.omok.domain.model.Turn
import woowacourse.omok.domain.view.InputView
import woowacourse.omok.domain.view.OutputView

class Controller {
    fun play() {
        gameStart(Board(BOARD_SIZE))
    }

    private fun gameStart(board: Board) {
        OutputView.printGameStart()
        OutputView.printBoard(board)
        val ruleAdapter = RuleAdapter(board)
        var turn: Turn = BlackTurn()
        var beforePoint: Point? = null
        while (turn !is FinishedTurn) {
            OutputView.printTurn(turn, beforePoint)
            val point = inputPoint(board, turn)
            turn = board.putStone(point, turn, ruleAdapter)
            beforePoint = point
            OutputView.printBoard(board)
        }
        OutputView.printWinner(turn)
    }

    private fun inputPoint(
        board: Board,
        turn: Turn,
    ): Point {
        runCatching {
            return InputView.readPoint(board)
        }.onFailure {
            OutputView.printInvalidPointInputMessage()
        }
        return inputPoint(board, turn)
    }

    companion object {
        private const val BOARD_SIZE = 15
    }
}
