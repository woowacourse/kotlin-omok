package omok.domain.omokboard

import omok.domain.omokboard.PointState.EMPTY
import omok.domain.omokboard.PointState.OCCUPIED_BLACK
import omok.domain.omokboard.PointState.OCCUPIED_WHITE
import omok.domain.player.StoneColor

data class Point(
    private var _state: PointState = EMPTY,
) {
    val state: PointState get() = _state

    fun updateState(stoneColor: StoneColor) {
        _state =
            when (stoneColor) {
                StoneColor.BLACK -> OCCUPIED_BLACK
                StoneColor.WHITE -> OCCUPIED_WHITE
            }
    }
}
