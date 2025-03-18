package omok.domain.state

import omok.domain.Point
import omok.domain.Stones

class WhiteTurn(
    override val blackStones: Stones,
    override val whiteStones: Stones,
) : State {
    override fun place(point: Point): BlackTurn {
        val newStones = whiteStones + point
        return BlackTurn(blackStones, newStones)
    }
}
