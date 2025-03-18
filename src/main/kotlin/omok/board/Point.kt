package omok.board

import omok.stone.Position
import omok.stone.StoneColor

class Point(val position: Position) {
    var state: PointState = PointState.OPEN
        private set

    fun changeColor(color: StoneColor) {
        if (color == StoneColor.WHITE) {
            changeState(PointState.WHITE)
            return
        }
        changeState(PointState.BLACK)
    }

    fun changeState(newState: PointState) {
        state = newState
    }
}
