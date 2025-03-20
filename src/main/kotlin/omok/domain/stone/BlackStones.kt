package omok.domain.stone

import rule.BlackRenjuRule
import rule.OmokRule
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
        return violateType.state
    }
}
