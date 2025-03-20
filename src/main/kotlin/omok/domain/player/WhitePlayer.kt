package omok.domain.player

import rule.OmokRule
import rule.WhiteRenjuRule
import rule.type.Violation
import rule.wrapper.point.Point

class WhitePlayer(width: Int, height: Int) : Player() {
    override val stonesBacking: MutableList<Point> = mutableListOf()
    override val rule: OmokRule = WhiteRenjuRule(width, height)

    override fun isViolation(
        otherStones: List<Point>,
        startPoint: Point,
    ) {
        dealViolation(Violation.NONE)
    }
}
