package omok.model.board

import omok.model.StoneColor

data class Point(val x: Int, val y: Int) {
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
