package omok.domain.stones

import rule.BlackRenjuRule
import rule.OmokRule
import rule.type.Violation
import rule.wrapper.point.Point

class BlackStones(
    points: Set<Point> = emptySet(),
) : Stones(points) {
    override val rule: OmokRule = BlackRenjuRule()

    override fun plus(point: Point): BlackStones = BlackStones(points + point)

    fun isFoul(
        other: Stones,
        point: Point,
    ): Boolean {
        val violateType =
            rule.checkAnyFoulCondition(this.points.toList(), other.points.toList(), point)
        return when (violateType) {
            Violation.DOUBLE_THREE, Violation.DOUBLE_FOUR, Violation.OVERLINE -> true
            Violation.NONE -> false
        }
    }
}
