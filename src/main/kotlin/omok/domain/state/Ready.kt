package omok.domain.state

import omok.domain.Point
import omok.domain.stone.BlackStones
import omok.domain.stone.StoneColor
import omok.domain.stone.WhiteStones

class Ready(
    override val blackStones: BlackStones = BlackStones(),
    override val whiteStones: WhiteStones = WhiteStones(),
) : Playing {
    override fun place(
        point: Point,
        boardSize: Int,
        onBoardUpdated: (Set<Point>, Set<Point>) -> Unit,
    ): WhiteTurn {
        val newStones = blackStones + point
        onBoardUpdated(newStones.points, whiteStones.points)
        return WhiteTurn(newStones, whiteStones)
    }

    override fun nextStoneColor(): StoneColor = StoneColor.BLACK
}
