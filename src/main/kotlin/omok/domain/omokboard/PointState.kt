package omok.domain.omokboard

import omok.domain.omokboard.State.EMPTY
import omok.domain.omokboard.State.OCCUPIED_BLACK
import omok.domain.omokboard.State.OCCUPIED_WHITE
import omok.domain.player.StoneColor
import omok.domain.player.StoneColor.BLACK
import omok.domain.player.StoneColor.WHITE

// sealed class PointState(){
//    data object Empty : PointState()
//    data class OCCUPIED(val color: Color) : PointState()
// }

class PointState(private var _state: State = EMPTY) {
    val state: State get() = _state

    fun updateState(stoneColor: StoneColor) {
        _state =
            when (stoneColor) {
                BLACK -> OCCUPIED_BLACK
                WHITE -> OCCUPIED_WHITE
            }
    }
}
