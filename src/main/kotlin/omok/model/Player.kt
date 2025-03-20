package omok.model

import rule.OmokRule
import rule.type.Violation
import rule.wrapper.point.Point

abstract class Player {
    abstract val points: Points
    abstract val rule: OmokRule

    fun isOccupied(
        newPoint: Point,
        otherPoints: Points,
    ): Boolean {
        return newPoint in points.points || newPoint in otherPoints.points
    }

    fun place(
        newPoint: Point,
        otherPoints: Points,
    ): GameState {
        require(!isOccupied(newPoint, otherPoints)) { ERROR_MESSAGE_IS_ALREADY_OCCUPIED }
        checkViolation(newPoint, otherPoints)
        points.add(newPoint)
        return checkGameState(newPoint)
    }

    abstract fun checkGameState(newPoint: Point): GameState

    private fun checkViolation(
        newPoint: Point,
        otherPoints: Points,
    ) {
        val violation: Violation = rule.checkAnyFoulCondition(points.points, otherPoints.points, newPoint)
        require(violation == Violation.NONE) {
            when (violation) {
                Violation.DOUBLE_THREE -> ERROR_MESSAGE_DOUBLE_THREE_VIOLATION
                Violation.DOUBLE_FOUR -> ERROR_MESSAGE_DOUBLE_FOUR_VIOLATION
                Violation.OVERLINE -> ERROR_MESSAGE_OVERLINE_VIOLATION
                Violation.NONE -> throw IllegalStateException()
            }
        }
    }

    companion object {
        private const val ERROR_MESSAGE_IS_ALREADY_OCCUPIED = "이미 돌이 있는 자리입니다."
        private const val ERROR_MESSAGE_DOUBLE_THREE_VIOLATION = "삼삼 금수입니다."
        private const val ERROR_MESSAGE_DOUBLE_FOUR_VIOLATION = "사사 금수입니다."
        private const val ERROR_MESSAGE_OVERLINE_VIOLATION = "장목 금수입니다."
    }
}
