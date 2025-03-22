package omok.domain.stone

import omok.domain.Point
import omok.domain.rule.RenjuRule

class BlackStones(
    points: Set<Point> = emptySet(),
) : Stones(points) {
    private val renjuRule = RenjuRule(points)

    override fun plus(point: Point): BlackStones = BlackStones(points + point)

    fun isFoul(
        other: Stones,
        point: Point,
    ): Boolean {
        return renjuRule.isFoul(other, point)
    }
}
