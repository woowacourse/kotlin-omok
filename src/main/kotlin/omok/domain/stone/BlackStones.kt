package omok.domain.stone

import omok.domain.Point
import omok.domain.rule.BlackStoneRule

class BlackStones(
    points: Set<Point> = emptySet(),
) : Stones(points) {
    override val rule = BlackStoneRule()

    override fun plus(point: Point): BlackStones = BlackStones(points + point)

    fun isFoul(
        other: Stones,
        point: Point,
    ): Boolean = rule.checkAnyFoulCondition(this.points.toList(), other.points.toList(), point)
}
