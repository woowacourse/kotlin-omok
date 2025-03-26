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
        onBoardUpdated: (Set<Point>, Set<Point>) -> Unit,
    ): State {
        require(!(blackStones.contains(point) || whiteStones.contains(point))) { ERROR_INVALID_POINT }

        val newStones = whiteStones + point
        return when {
            whiteStones.isOmok(point) -> Finished.Win(StoneColor.WHITE)
            blackStones.points.size + newStones.points.size >= boardSize * boardSize -> Finished.Draw
            else -> BlackTurn(blackStones, newStones)
        }.also {
            onBoardUpdated(blackStones.points, newStones.points)
        }
    }

    override fun nextStoneColor(): StoneColor = StoneColor.WHITE

    companion object {
        private const val ERROR_INVALID_POINT = "[ERROR] 이미 돌이 놓여져 있습니다."
    }
}
