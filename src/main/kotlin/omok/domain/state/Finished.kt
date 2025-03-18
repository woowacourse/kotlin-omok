package omok.domain.state

import omok.domain.Point
import omok.domain.Stones

class Finished(
    override val blackStones: Stones,
    override val whiteStones: Stones,
) : State {
    override fun place(point: Point): State = throw IllegalStateException()
}
