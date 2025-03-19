package omok.model

import rule.OmokRule
import rule.type.Violation
import rule.wrapper.point.Point

abstract class Player(
    val rule: OmokRule,
    val points: Points,
) {
    abstract fun place(
        newPoint: Point,
        otherPoints: Points,
    )

    abstract fun checkViolation(
        newPoint: Point,
        otherPoints: Points,
    ): Violation

    fun isOccupied(
        newPoint: Point,
        otherPoints: Points,
    ): Boolean {
        return (points.contains(newPoint) || otherPoints.contains(newPoint))
    }

    companion object {
        const val ERROR_MESSAGE_POINT_IS_OCCUPIED = "이미 돌이 있는 자리입니다."
    }
}
