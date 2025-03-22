package omok.domain.state

import omok.domain.Point
import omok.domain.stone.BlackStones
import omok.domain.stone.StoneColor
import omok.domain.stone.WhiteStones

class WhiteTurn(
    override val blackStones: BlackStones,
    override val whiteStones: WhiteStones,
) : Playing {
    override fun place(
        point: Point,
        boardSize: Int,
    ): State {
        require(!(blackStones.contains(point) || whiteStones.contains(point))) { ERROR_INVALID_POINT }

        val newStones = whiteStones + point
        return when {
            whiteStones.isOmok(point) -> WhiteWin(blackStones, newStones)
            blackStones.points.size + newStones.points.size >= boardSize * boardSize ->
                Draw(blackStones, newStones)
            else -> BlackTurn(blackStones, newStones)
        }
    }

    override fun lastStonePoint(): Point = blackStones.lastStonePoint()

    override fun nextStoneColor(): StoneColor = StoneColor.WHITE

    companion object {
        private const val ERROR_INVALID_POINT = "[ERROR] 이미 돌이 놓여져 있습니다."
    }
}
