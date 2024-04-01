package woowacourse.omok.domain.controller

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.FinishedTurn
import woowacourse.omok.domain.model.OmokGame
import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.Turn
import woowacourse.omok.domain.view.InputView
import woowacourse.omok.domain.view.OutputView

class Controller {
    private val omokGame: OmokGame = OmokGame(BOARD_SIZE)

    fun play() {
        gameStart()
    }

    private fun gameStart() {
        OutputView.printGameStart()
        OutputView.printBoard(omokGame.board)
        while (omokGame.turn !is FinishedTurn) {
            OutputView.printTurn(omokGame.turn, omokGame.beforePoint)
            val point = inputPoint(omokGame.board, omokGame.turn)
            omokGame.updateTurn(omokGame.board.putStone(point, omokGame.turn, omokGame.ruleAdapter))
            omokGame.updateBeforePoint(point)
            OutputView.printBoard(omokGame.board)
        }
        OutputView.printWinner(omokGame.turn)
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
