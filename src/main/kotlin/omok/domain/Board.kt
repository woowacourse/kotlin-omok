package omok.domain

import omok.domain.state.Finished
import omok.domain.state.Ready
import omok.domain.state.State

class Board(
    state: State = Ready(),
) {
    var state = state
        private set

    fun playOmok(
        onTurn: (StoneColor, Point?) -> Unit,
        onPointInput: () -> Point,
        onBoardUpdated: (Set<Point>, Set<Point>) -> Unit,
    ) {
        while (state !is Finished) {
            onTurn(state.nextStoneColor(), state.lastStonePoint())
            place(onPointInput())
            onBoardUpdated(state.blackStones.points, state.whiteStones.points)
        }
    }

    private fun place(point: Point) {
        state = state.place(point)
    }
}
