package domain.domain.state

import domain.domain.Point
import domain.domain.stone.BlackStones
import domain.domain.stone.StoneColor
import domain.domain.stone.WhiteStones

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
