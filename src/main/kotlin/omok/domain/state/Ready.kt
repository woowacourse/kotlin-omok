package omok.domain.state

import omok.domain.Point
import omok.domain.StoneColor
import omok.domain.Stones

class Ready(
    override val blackStones: Stones = Stones(color = StoneColor.BLACK),
    override val whiteStones: Stones = Stones(color = StoneColor.WHITE),
) : State {
    override fun place(point: Point): WhiteTurn {
        val newStones = blackStones + point
        return WhiteTurn(newStones, whiteStones)
    }
}
