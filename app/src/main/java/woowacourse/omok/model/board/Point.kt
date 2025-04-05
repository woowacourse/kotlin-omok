package woowacourse.omok.model.board

import woowacourse.omok.model.StoneColor

data class Point(
    val x: Int,
    val y: Int,
) {
    var state: PointState = PointState.OPEN
        private set

    fun changeState(color: StoneColor) {
        state =
            when (color) {
                StoneColor.BLACK -> PointState.BLACK
                StoneColor.WHITE -> PointState.WHITE
            }
    }
}
