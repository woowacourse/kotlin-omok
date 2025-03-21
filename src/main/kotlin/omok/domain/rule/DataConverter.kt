package omok.domain.rule

import omok.domain.OmokViolation
import omok.domain.point.OmokPoint
import rule.type.Violation
import rule.wrapper.point.Point

class DataConverter {
    fun convertOmokPointToPoint(omokPoint: OmokPoint): Point {
        return Point(omokPoint.row.value, omokPoint.col.value)
    }

    fun convertSetToList(stones: Set<OmokPoint>): List<Point> {
        return stones.toList().map { convertOmokPointToPoint(it) }
    }

    fun convertViolation(violation: Violation): OmokViolation {
        return when (violation) {
            Violation.DOUBLE_THREE -> OmokViolation.DOUBLE_THREE
            Violation.DOUBLE_FOUR -> OmokViolation.DOUBLE_FOUR
            Violation.OVERLINE -> OmokViolation.OVER_LINE
            Violation.NONE -> OmokViolation.NONE
        }
    }
}
