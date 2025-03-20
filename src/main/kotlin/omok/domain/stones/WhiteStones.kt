package omok.domain.stones

import rule.OmokRule
import rule.WhiteRenjuRule
import rule.wrapper.point.Point

class WhiteStones(
    points: Set<Point> = emptySet(),
) : Stones(points) {
    override val rule: OmokRule = WhiteRenjuRule()

    override fun plus(point: Point): WhiteStones = WhiteStones(points + point)
}
