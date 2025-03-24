package omok.domain.rule

import omok.domain.Board.Companion.DEFAULT_BOARD_SIZE
import omok.domain.Point
import omok.domain.stone.Stones
import rule.facade.BlackRenjuRule

class RenjuRule(boardSize: Int = DEFAULT_BOARD_SIZE) {
    private val omokRule = BlackRenjuRule(boardSize, boardSize)

    fun isFoul(
        other: Stones,
        newPoint: Point,
        existingPoints: Set<Point>,
    ): Boolean {
        val selfPoints = existingPoints.map { it.toPair() }
        val otherPoints = other.points.map { it.toPair() }
        val targetPoint = newPoint.toPair()

        return listOf(
            omokRule.checkDoubleFourFoul(selfPoints, otherPoints, targetPoint),
            omokRule.checkDoubleThreeFoul(selfPoints, otherPoints, targetPoint),
            omokRule.checkOverline(selfPoints, targetPoint),
        ).any { it }
    }

    private fun Point.toPair() = Pair(this.x, this.y)
}
