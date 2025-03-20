package omok.domain.player

import omok.domain.OmokGrid
import rule.BlackRenjuRule
import rule.OmokRule
import rule.type.Violation
import rule.wrapper.point.Point

class BlackPlayer : Player() {
    override val stonesBacking: MutableList<Point> = mutableListOf()
    override val rule: OmokRule = BlackRenjuRule(OmokGrid.DEFAULT_SIZE, OmokGrid.DEFAULT_SIZE)

    override fun isViolation(
        otherStones: List<Point>,
        startPoint: Point,
    ): Boolean {
        val violation = rule.checkAnyFoulCondition(stonesBacking, otherStones, startPoint)
        return violation != Violation.NONE
    }
}
