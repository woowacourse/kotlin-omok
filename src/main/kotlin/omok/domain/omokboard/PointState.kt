package omok.domain.omokboard

import omok.domain.player.StoneColor

sealed class PointState {
    data object Empty : PointState()

    data class OCCUPIED(val color: StoneColor) : PointState()
}
