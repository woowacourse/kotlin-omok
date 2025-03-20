package omok.domain.state

import omok.domain.stone.BlackStones
import omok.domain.stone.StoneColor
import omok.domain.stone.WhiteStones
import rule.wrapper.point.Point

class BlackTurn(
    override val blackStones: BlackStones,
    override val whiteStones: WhiteStones,
) : State {
    override fun place(
        point: Point,
        boardSize: Int,
    ): State {
        require(!(blackStones.contains(point) || whiteStones.contains(point))) { ERROR_INVALID_POINT }
        require(!blackStones.isFoul(whiteStones, point)) { ERROR_RENJU_RULE }

        val newStones = blackStones + point
        return when {
            blackStones.isOmok(whiteStones, point) -> BlackWin(newStones, whiteStones)
            newStones.points.size + whiteStones.points.size >= boardSize * boardSize ->
                Draw(newStones, whiteStones)
            else -> WhiteTurn(newStones, whiteStones)
        }
    }

    override fun lastStonePoint(): Point = whiteStones.lastStonePoint()

    override fun nextStoneColor(): StoneColor = StoneColor.BLACK

    companion object {
        private const val ERROR_INVALID_POINT = "[ERROR] 이미 돌이 놓여져 있습니다."
        private const val ERROR_RENJU_RULE = "[ERROR] 돌을 놓을 수 없습니다."
    }
}
