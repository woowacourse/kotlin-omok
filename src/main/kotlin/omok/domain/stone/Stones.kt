package omok.domain.stone

import omok.domain.Point
import omok.domain.rule.OmokRule
import omok.domain.rule.Violation

class Stones(
    private val rule: OmokRule,
    points: Set<Point> = emptySet(),
) {
    private val _points = points.toMutableSet()
    val points = _points.toSet()

    operator fun plus(point: Point): Stones = Stones(rule, _points + point)

    fun contains(point: Point): Boolean = point in _points

    fun isOmok(lastPoint: Point): Boolean = rule.isOmok(_points, lastPoint)

    fun isFoul(
        other: Stones,
        point: Point,
    ): Boolean {
        val violation = rule.checkViolation(this.points, other.points, point)
        return when (violation) {
            Violation.DOUBLE_THREE, Violation.DOUBLE_FOUR, Violation.OVERLINE -> true
            Violation.NONE -> false
        }
    }
}
