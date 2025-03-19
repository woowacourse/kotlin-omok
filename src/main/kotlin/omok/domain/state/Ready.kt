package omok.domain.state

import omok.domain.StoneColor
import omok.domain.Stones
import rule.wrapper.point.Point

class Ready(
    override val blackStones: Stones = Stones(color = StoneColor.BLACK),
    override val whiteStones: Stones = Stones(color = StoneColor.WHITE),
) : State {
    override fun place(
        point: Point,
        boardSize: Int,
    ): WhiteTurn {
        val newStones = blackStones + point
        return WhiteTurn(newStones, whiteStones)
    }

    override fun lastStonePoint(): Point? = null

    override fun nextStoneColor(): StoneColor = StoneColor.BLACK
}
