package omok.domain.state

import omok.domain.StoneColor
import omok.domain.Stones
import rule.WhiteRenjuRule
import rule.wrapper.point.Point

class WhiteTurn(
    override val blackStones: Stones,
    override val whiteStones: Stones,
) : State {
    override fun place(
        point: Point,
        boardSize: Int,
    ): State {
        require(!(blackStones.contains(point) || whiteStones.contains(point))) { ERROR_INVALID_POINT }

        val newStones = whiteStones + point

        val rule = WhiteRenjuRule(boardSize)
        val isOmok = rule.checkWin(whiteStones.points.toList(), blackStones.points.toList(), point)
        if (isOmok) {
            return WhiteWin(blackStones, newStones)
        }

        if (blackStones.points.size + newStones.points.size >= boardSize * boardSize) {
            return Draw(blackStones, newStones)
        }
        return BlackTurn(blackStones, newStones)
    }

    override fun lastStonePoint(): Point = blackStones.lastStonePoint()

    override fun nextStoneColor(): StoneColor = StoneColor.WHITE

    companion object {
        private const val ERROR_INVALID_POINT = "이미 돌이 놓여져 있습니다."
    }
}
