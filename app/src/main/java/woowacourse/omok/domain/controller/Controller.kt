package woowacourse.omok.domain.controller

import woowacourse.omok.domain.model.BlackTurn
import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.OmokGame
import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.view.InputView
import woowacourse.omok.domain.view.OutputView

class Controller {
    fun run() {
        OutputView.printGameStart()
        val board = Board()
        val turn = BlackTurn()
        val omokGame = OmokGame(turn, board)
        OutputView.printBoard(board)
        OutputView.printTurn(turn, null)
        do {
            omokGame.tryPlayTurn(OutputView::printBoard, OutputView::printTurn, ::getPoint, OutputView::printFailureMessage)
        } while (!omokGame.isGameFinished())
    }

    private tailrec fun getPoint(): Point = runCatching { InputView.readPoint() }.getOrNull() ?: getPoint()
}
