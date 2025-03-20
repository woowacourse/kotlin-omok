package omok.domain.state

import omok.domain.StoneColor
import omok.domain.stones.BlackStones
import omok.domain.stones.WhiteStones
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

        val newStones = blackStones + point
        if (blackStones.isFoul(whiteStones, point)) {
            println(ERROR_RENJU_RULE)
            return BlackTurn(blackStones, whiteStones)
        }
        if (blackStones.isOmok(whiteStones, point)) {
            return BlackWin(newStones, whiteStones)
        }
        if (newStones.points.size + whiteStones.points.size >= boardSize * boardSize) {
            return Draw(newStones, whiteStones)
        }
        return WhiteTurn(newStones, whiteStones)
    }

    override fun lastStonePoint(): Point = whiteStones.lastStonePoint()

    override fun nextStoneColor(): StoneColor = StoneColor.BLACK

    companion object {
        private const val ERROR_INVALID_POINT = "[ERROR] 이미 돌이 놓여져 있습니다."
        private const val ERROR_RENJU_RULE = "[ERROR] 돌을 놓을 수 없습니다."
    }
}
