package woowacourse.omok.domain

import woowacourse.omok.domain.board.CellState
import woowacourse.omok.domain.board.Point

class GameState {
    var isFinished: Boolean = false
        private set
    var previousPoint: Point? = null
        private set
    var currentCellState: CellState = CellState.BLACK
        private set

    fun start(
        lastMove: Pair<Point, CellState>?,
        isFinished: Boolean,
    ) {
        this.isFinished = isFinished
        previousPoint = lastMove?.first
        currentCellState = lastMove?.second?.reverseCellState() ?: CellState.BLACK
    }

    fun updateState(point: Point) {
        previousPoint = point
        currentCellState = currentCellState.reverseCellState()
    }

    fun finishGame() {
        isFinished = true
    }
}
