package domain.domain.state

import domain.domain.Point
import domain.domain.stone.BlackStones
import domain.domain.stone.StoneColor
import domain.domain.stone.WhiteStones

class BlackTurn(
    override val blackStones: BlackStones,
    override val whiteStones: WhiteStones,
) : Playing {
    override fun place(
        point: Point,
        boardSize: Int,
        onBoardUpdated: (BlackStones, WhiteStones) -> Unit,
    ): State {
        val newStones = blackStones + point
        return when {
            blackStones.isOmok(point) -> Finished.Win(StoneColor.BLACK)
            newStones.points.size + whiteStones.points.size >= boardSize * boardSize -> Finished.Draw
            blackStones.isDoubleThreeFoul(whiteStones, point) -> Foul.DoubleThree
            blackStones.isDoubleFourFoul(whiteStones, point) -> Foul.DoubleFour
            blackStones.isOverLine(point) -> Foul.OverLine
            blackStones.contains(point) || whiteStones.contains(point) -> Foul.Duplicated
            else -> WhiteTurn(newStones, whiteStones)
        }.also {
            onBoardUpdated(newStones, whiteStones)
        }
    }

    override fun nextStoneColor(): StoneColor = StoneColor.BLACK
}
