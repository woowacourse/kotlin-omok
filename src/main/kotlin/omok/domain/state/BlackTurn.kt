package omok.domain.state

import omok.domain.Point
import omok.domain.Stone
import omok.domain.StoneColor
import omok.domain.Stones

class BlackTurn(
    override val blackStones: Stones,
    override val whiteStones: Stones,
) : State {
    override fun place(point: Point): WhiteTurn {
        val newStones = blackStones + Stone(point, StoneColor.BLACK)
        return WhiteTurn(newStones, whiteStones)
    }
}
