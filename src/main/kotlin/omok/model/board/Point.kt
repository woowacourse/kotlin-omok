package omok.model.board

import omok.model.StoneColor

data class Point(val x: Int, val y: Int) {
    var state: PointState = PointState.OPEN
        private set

    fun changeColor(color: StoneColor) {
        state =
            when (color) {
                StoneColor.WHITE -> PointState.WHITE
                StoneColor.BLACK -> PointState.BLACK
            }
    }

    fun changeState(newState: PointState) {
        state = newState
    }
}
