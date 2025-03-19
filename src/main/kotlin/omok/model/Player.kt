package omok.model

import rule.OmokRule
import rule.type.Violation
import rule.wrapper.point.Point

abstract class Player {
    abstract val points: List<Point>
    abstract val rule: OmokRule

    fun isOccupied(
        newPoint: Point,
        otherPoints: List<Point>,
    ): Boolean {
        return newPoint in points || newPoint in otherPoints
    }

    abstract fun place(
        newPoint: Point,
        otherPoints: List<Point>,
    )

    abstract fun checkViolation(
        newPoint: Point,
        otherPoints: List<Point>,
    ): Violation
}
