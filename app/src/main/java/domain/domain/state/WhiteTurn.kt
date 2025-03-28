package domain.domain.state

import domain.domain.Point
import domain.domain.stone.BlackStones
import domain.domain.stone.StoneColor
import domain.domain.stone.WhiteStones

class WhiteTurn(
    override val blackStones: BlackStones,
    override val whiteStones: WhiteStones,
) : Playing {
    override fun place(
        point: Point,
        boardSize: Int,
        onBoardUpdated: (BlackStones, WhiteStones) -> Unit,
    ): State {
        val newStones = whiteStones + point
        return when {
            whiteStones.isOmok(point) -> Finished.Win(StoneColor.WHITE)
            blackStones.points.size + newStones.points.size >= boardSize * boardSize -> Finished.Draw
            blackStones.contains(point) || whiteStones.contains(point) -> Foul.Duplicated
            else -> BlackTurn(blackStones, newStones)
        }.also {
            onBoardUpdated(blackStones, newStones)
        }
    }

    override fun nextStoneColor(): StoneColor = StoneColor.WHITE
}
