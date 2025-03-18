package omok.domain.state

import omok.domain.Point
import omok.domain.Stones

class BlackTurn(
    override val blackStones: Stones,
    override val whiteStones: Stones,
) : State {
    override fun place(point: Point): WhiteTurn {
        val newStones = blackStones + point
        return WhiteTurn(newStones, whiteStones)
    }
}
