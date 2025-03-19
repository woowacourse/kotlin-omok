package omok.domain.state

import omok.domain.Point
import omok.domain.StoneColor
import omok.domain.Stones

interface State {
    val whiteStones: Stones
    val blackStones: Stones

    fun place(point: Point): State

    fun lastStonePoint(): Point?

    fun nextStoneColor(): StoneColor
}
