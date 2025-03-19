package omok.domain.state

import omok.domain.Point
import omok.domain.StoneColor
import omok.domain.Stones

class BlackTurn(
    override val blackStones: Stones,
    override val whiteStones: Stones,
) : State {
    override fun place(point: Point): State {
        require(!(blackStones.contains(point) || whiteStones.contains(point))) { ERROR_INVALID_POINT }

        val newStones = blackStones + point
        if (newStones.isOmok()) {
            return BlackWin(newStones, whiteStones)
        }
        return WhiteTurn(newStones, whiteStones)
    }

    override fun lastStonePoint(): Point = whiteStones.lastStonePoint()

    override fun nextStoneColor(): StoneColor = StoneColor.BLACK

    companion object {
        private const val ERROR_INVALID_POINT = "이미 돌이 놓여져 있습니다."
    }
}
