package omok.model.domain.omokboard

import omok.model.domain.omokboard.PointState.EMPTY
import omok.model.domain.omokboard.PointState.OCCUPIED_BLACK
import omok.model.domain.omokboard.PointState.OCCUPIED_WHITE
import omok.model.domain.player.StoneColor

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
