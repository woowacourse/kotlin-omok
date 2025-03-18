package omok.domain

import omok.domain.state.BlackTurn
import omok.domain.state.Ready
import omok.domain.state.State
import omok.domain.state.WhiteTurn

class Board(
    state: State = Ready(),
) {
    var state = state
        private set

    fun place(point: Point) {
        state = state.place(point)
    }

    fun lastStonePoint(): Point =
        when (state) {
            is BlackTurn -> state.whiteStones.lastStonePoint()
            is WhiteTurn -> state.blackStones.lastStonePoint()
            else -> throw IllegalStateException()
        }
}
