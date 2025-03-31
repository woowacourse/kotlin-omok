package woowacourse.omok.domain

import woowacourse.omok.domain.board.Point

class GameState(
    isFinished: Boolean = false,
    lastMovePoint: Point? = null,
) {
    var isFinished: Boolean = isFinished
        private set
    var previousPoint: Point? = lastMovePoint
        private set

    fun updateLastMovePoint(point: Point) {
        previousPoint = point
    }

    fun finishGame() {
        isFinished = true
    }
}
