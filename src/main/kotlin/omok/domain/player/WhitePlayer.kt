package omok.domain.player

import omok.domain.OmokGrid
import rule.OmokRule
import rule.WhiteRenjuRule
import rule.type.Violation
import rule.wrapper.point.Point

class WhitePlayer : Player() {
    override val stonesBacking: MutableList<Point> = mutableListOf()
    override val rule: OmokRule = WhiteRenjuRule(OmokGrid.DEFAULT_SIZE, OmokGrid.DEFAULT_SIZE)

    override fun isViolation(
        otherStones: List<Point>,
        startPoint: Point,
    ) {
        dealViolation(Violation.NONE)
    }
}
