package woowacourse.omok.domain.controller

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.OmokGame
import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.Turn
import woowacourse.omok.domain.view.InputView
import woowacourse.omok.domain.view.OutputView

class Controller {
    private val omokGame: OmokGame = OmokGame()

    fun play() {
        gameStart()
    }

    private fun gameStart() {
        OutputView.printGameStart()
        OutputView.printBoard(omokGame.board)
        while (omokGame.onGame()) {
            OutputView.printTurn(omokGame.turn, omokGame.board.beforePoint)
            val point = inputPoint(omokGame.board, omokGame.turn)
            if (omokGame.putStone(point)) {
                OutputView.printBoard(omokGame.board)
            } else {
                OutputView.printInvalidPointInputMessage()
            }
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
}
