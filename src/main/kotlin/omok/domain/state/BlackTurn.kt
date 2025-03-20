package omok.domain.state

import omok.domain.StoneColor
import omok.domain.Stones
import rule.BlackRenjuRule
import rule.type.Violation
import rule.wrapper.point.Point

class BlackTurn(
    override val blackStones: Stones,
    override val whiteStones: Stones,
) : State {
    override fun place(
        point: Point,
        boardSize: Int,
    ): State {
        require(!(blackStones.contains(point) || whiteStones.contains(point))) { ERROR_INVALID_POINT }

        val newStones = blackStones + point

        val rule = BlackRenjuRule(boardSize)
        val violateType = rule.checkAnyFoulCondition(blackStones.points.toList(), whiteStones.points.toList(), point)
        when (violateType) {
            Violation.DOUBLE_THREE, Violation.DOUBLE_FOUR, Violation.OVERLINE -> {
                println(ERROR_RENJU_RULE)
                return BlackTurn(blackStones, whiteStones)
            }
            Violation.NONE -> {
                val isOmok = rule.checkWin(blackStones.points.toList(), whiteStones.points.toList(), point)
                if (isOmok) {
                    return BlackWin(newStones, whiteStones)
                }
            }
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
