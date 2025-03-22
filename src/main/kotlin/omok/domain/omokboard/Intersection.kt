package omok.domain.omokboard

import omok.domain.omokboard.IntersectionState.EMPTY
import omok.domain.omokboard.IntersectionState.OCCUPIED_BLACK
import omok.domain.omokboard.IntersectionState.OCCUPIED_WHITE
import omok.domain.player.StoneColor
import omok.domain.player.StoneColor.BLACK
import omok.domain.player.StoneColor.WHITE

data class Intersection(
    private var _state: IntersectionState = EMPTY,
) {
    val state: IntersectionState get() = _state

    fun updateState(stoneColor: StoneColor) {
        _state =
            when (stoneColor) {
                BLACK -> OCCUPIED_BLACK
                WHITE -> OCCUPIED_WHITE
            }
    }
}
