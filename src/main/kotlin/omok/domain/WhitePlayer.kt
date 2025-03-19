package omok.domain

import rule.OmokRule
import rule.WhiteRenjuRule
import rule.wrapper.point.Point

class WhitePlayer : Player() {
    override val stonesBacking: MutableList<Point> = mutableListOf()
    override val rule: OmokRule = WhiteRenjuRule()

    override fun isViolation(
        otherStones: List<Point>,
        startPoint: Point,
    ): Boolean {
        return false
    }
}
