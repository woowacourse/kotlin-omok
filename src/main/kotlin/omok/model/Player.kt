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


    fun checkViolation(
        newPoint: Point,
        otherPoints: List<Point>,
    ) {
        val violation: Violation = rule.checkAnyFoulCondition(points, otherPoints, newPoint)
        require(violation == Violation.NONE) {
            when (violation) {
                Violation.DOUBLE_THREE -> ERROR_MESSAGE_DOUBLE_THREE_VIOLATION
                Violation.DOUBLE_FOUR -> ERROR_MESSAGE_DOUBLE_FOUR_VIOLATION
                Violation.OVERLINE -> ERROR_MESSAGE_OVERLINE_VIOLATION
                Violation.NONE -> throw IllegalStateException()
            }
        }
    }

    abstract fun place(
        newPoint: Point,
        otherPoints: List<Point>,
    )

    companion object {
        private const val ERROR_MESSAGE_DOUBLE_THREE_VIOLATION = "삼삼 금수입니다."
        private const val ERROR_MESSAGE_DOUBLE_FOUR_VIOLATION = "사사 금수입니다."
        private const val ERROR_MESSAGE_OVERLINE_VIOLATION = "장목 금수입니다."
    }
}
