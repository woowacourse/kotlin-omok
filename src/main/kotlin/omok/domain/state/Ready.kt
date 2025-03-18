package omok.domain.state

import omok.domain.Point
import omok.domain.Stone
import omok.domain.StoneColor
import omok.domain.Stones

class Ready(
    override val blackStones: Stones = Stones(),
    override val whiteStones: Stones = Stones(),
) : State {
    override fun place(point: Point): WhiteTurn {
        val newStones = blackStones + Stone(point, StoneColor.BLACK)
        return WhiteTurn(newStones, whiteStones)
    }
}
