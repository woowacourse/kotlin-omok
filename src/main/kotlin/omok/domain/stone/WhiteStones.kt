package omok.domain.stone

import omok.domain.Point
import omok.domain.rule.WhiteStoneRule

class WhiteStones(
    points: Set<Point> = emptySet(),
) : Stones(points) {
    override val rule = WhiteStoneRule()

    override fun plus(point: Point): WhiteStones = WhiteStones(points + point)
}
