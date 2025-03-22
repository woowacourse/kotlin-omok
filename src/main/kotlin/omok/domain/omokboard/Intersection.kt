package omok.domain.omokboard

import omok.domain.omokboard.PointState.EMPTY
import omok.domain.omokboard.PointState.OCCUPIED_BLACK
import omok.domain.omokboard.PointState.OCCUPIED_WHITE
import omok.domain.player.StoneColor
import omok.domain.player.StoneColor.BLACK
import omok.domain.player.StoneColor.WHITE

data class Intersection(
    private var _state: PointState = EMPTY,
) {
    val state: PointState get() = _state

    fun updateState(stoneColor: StoneColor) {
        _state =
            when (stoneColor) {
                BLACK -> OCCUPIED_BLACK
                WHITE -> OCCUPIED_WHITE
            }
    }
}
