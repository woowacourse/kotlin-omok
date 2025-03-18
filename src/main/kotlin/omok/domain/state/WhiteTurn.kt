package omok.domain.state

import omok.domain.Point
import omok.domain.Stone
import omok.domain.StoneColor

class WhiteTurn(
    override val stones: List<Stone>,
) : State {
    override fun place(point: Point): BlackTurn {
        val newStones = stones + Stone(point, StoneColor.WHITE)
        return BlackTurn(newStones)
    }
}
