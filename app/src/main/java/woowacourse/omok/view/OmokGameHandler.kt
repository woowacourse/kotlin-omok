package woowacourse.omok.view

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Point
import woowacourse.omok.model.board.StoneColor

interface OmokGameHandler {
    fun onStartGame()

    fun onRequestPosition(previousPoint: Pair<Point?, StoneColor>): Point

    fun onBoardUpdated(board: Board)

    fun onGameWon(winnerState: StoneColor?)

    fun onError(message: String)
}
