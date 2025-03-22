package omok.domain.rule

import omok.domain.Board.Companion.DEFAULT_BOARD_SIZE
import omok.domain.Point
import omok.domain.stone.Stones
import rule.facade.BlackRenjuRule

class RenjuRule(boardSize: Int = DEFAULT_BOARD_SIZE) {
    private val omokRule = BlackRenjuRule(boardSize, boardSize)

    fun isFoul(
        other: Stones,
        point: Point,
        points: Set<Point>,
    ): Boolean {
        val selfPoints = points.map { it.toPair() }
        val otherPoints = other.points.map { it.toPair() }
        val targetPoint = point.toPair()

        return listOf(
            omokRule.checkDoubleFourFoul(selfPoints, otherPoints, targetPoint),
            omokRule.checkDoubleThreeFoul(selfPoints, otherPoints, targetPoint),
            omokRule.checkOverline(selfPoints, targetPoint),
        ).any { it }
    }

    private fun Point.toPair() = Pair(this.x, this.y)
}
