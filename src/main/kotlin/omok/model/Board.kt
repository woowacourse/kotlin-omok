package omok.model

import rule.OmokRule
import rule.type.Violation
import rule.wrapper.point.Point
import java.lang.IllegalStateException

class Board {
    val board: List<MutableList<IntersectionState>> = List(16) { MutableList(16) { IntersectionState.EMPTY } }
    private var _lastStone: Intersection = Intersection(Point(1, 1), IntersectionState.EMPTY)
    val lastStone: Intersection get() = _lastStone.copy()

    val blackPoints: MutableList<Point> = mutableListOf()
    val whitePoints: MutableList<Point> = mutableListOf()

    fun place(
        newIntersection: Intersection,
        rule: OmokRule,
    ): BoardState {
        val violation: Violation = rule.checkAnyFoulCondition(blackPoints, whitePoints, newIntersection.point)
        require(violation == Violation.NONE) {
            when (violation) {
                Violation.DOUBLE_THREE -> ERROR_MESSAGE_DOUBLE_THREE_VIOLATION
                Violation.DOUBLE_FOUR -> ERROR_MESSAGE_DOUBLE_FOUR_VIOLATION
                Violation.OVERLINE -> ERROR_MESSAGE_OVERLINE_VIOLATION
                Violation.NONE -> throw IllegalStateException()
            }
        }

        val newPoint: Point = newIntersection.point
        val player: IntersectionState = newIntersection.state

        if (player == IntersectionState.BLACK) {
            blackPoints.add(newIntersection.point)
            board[newIntersection.point.row][newIntersection.point.col] = newIntersection.state
            if (rule.checkSerialSameStonesBiDirection(blackPoints, newPoint, 5)) return BoardState.BLACK_OMOK
        } else {
            whitePoints.add(newIntersection.point)
            board[newIntersection.point.row][newIntersection.point.col] = newIntersection.state
            if (rule.checkSerialSameStonesBiDirection(whitePoints, newPoint, 5)) return BoardState.WHITE_OMOK
        }
        setLastStone(newIntersection)
        return BoardState.PLAYING
    }

    private fun setLastStone(intersection: Intersection) {
        _lastStone = intersection
    }

    companion object {
        private const val ERROR_MESSAGE_INTERSECTION_NOT_EMPTY = "돌은 빈 칸에만 둘 수 있습니다."
        private const val ERROR_MESSAGE_DOUBLE_THREE_VIOLATION = "삼삼은 금수입니다."
        private const val ERROR_MESSAGE_DOUBLE_FOUR_VIOLATION = "사사는 금수입니다."
        private const val ERROR_MESSAGE_OVERLINE_VIOLATION = "장목은 금수입니다."
    }
}
