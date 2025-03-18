package omok.domain.state

import omok.domain.Point
import omok.domain.Stone
import omok.domain.StoneColor

class BlackTurn(
    override val stones: List<Stone>,
) : State {
    override fun place(point: Point): WhiteTurn {
        val newStones = stones + Stone(point, StoneColor.BLACK)
        return WhiteTurn(newStones)
    }
}
