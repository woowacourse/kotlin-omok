package omok.domain.state

import omok.domain.stone.BlackStones
import omok.domain.stone.StoneColor
import omok.domain.stone.WhiteStones
import rule.wrapper.point.Point

class Ready(
    override val blackStones: BlackStones = BlackStones(),
    override val whiteStones: WhiteStones = WhiteStones(),
) : Playing {
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
