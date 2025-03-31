package woowacourse.omok.domain

import woowacourse.omok.domain.board.CellState
import woowacourse.omok.domain.board.Point

class GameState(
    isFinished: Boolean = false,
    lastMove: Pair<Point, CellState>? = null,
) {
    var isFinished: Boolean = isFinished
        private set
    var previousPoint: Point? = lastMove?.first
        private set

    fun updateLastMovePoint(point: Point) {
        previousPoint = point
    }

    fun finishGame() {
        isFinished = true
    }
}
