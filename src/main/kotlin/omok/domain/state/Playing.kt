package omok.domain.state

import omok.domain.Point
import omok.domain.stone.StoneColor

interface Playing : State {
    fun place(point: Point): State

    fun nextStoneColor(): StoneColor
}
