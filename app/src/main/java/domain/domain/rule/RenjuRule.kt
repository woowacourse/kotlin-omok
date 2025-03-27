package domain.domain.rule

import domain.domain.Board.Companion.DEFAULT_BOARD_SIZE
import domain.domain.Point
import domain.domain.stone.Stones
import rule.facade.BlackRenjuRule

class RenjuRule(boardSize: Int = DEFAULT_BOARD_SIZE) {
    private val omokRule = BlackRenjuRule(boardSize, boardSize)

    fun isDoubleThree(
        other: Stones,
        newPoint: Point,
        existingPoints: Set<Point>,
    ): Boolean {
        val selfPoints = existingPoints.map { it.toPair() }
        val otherPoints = other.points.map { it.toPair() }
        val targetPoint = newPoint.toPair()

        return omokRule.checkDoubleThreeFoul(selfPoints, otherPoints, targetPoint)
    }

    fun isDoubleFour(
        other: Stones,
        newPoint: Point,
        existingPoints: Set<Point>,
    ): Boolean {
        val selfPoints = existingPoints.map { it.toPair() }
        val otherPoints = other.points.map { it.toPair() }
        val targetPoint = newPoint.toPair()

        return omokRule.checkDoubleFourFoul(selfPoints, otherPoints, targetPoint)
    }

    fun isOverLine(
        newPoint: Point,
        existingPoints: Set<Point>,
    ): Boolean {
        val selfPoints = existingPoints.map { it.toPair() }
        val targetPoint = newPoint.toPair()

        return omokRule.checkOverline(selfPoints, targetPoint)
    }

    private fun Point.toPair() = Pair(this.x, this.y)
}
