package omok.domain.state

import omok.domain.Point
import omok.domain.StoneColor
import omok.domain.Stones

class Finished(
    override val blackStones: Stones,
    override val whiteStones: Stones,
) : State {
    override fun place(point: Point): State = throw IllegalStateException()

    override fun lastStonePoint(): Point = throw IllegalStateException()

    override fun nextStoneColor(): StoneColor = throw IllegalStateException()
}
