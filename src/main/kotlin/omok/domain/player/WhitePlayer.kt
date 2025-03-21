package omok.domain.player

import omok.domain.OmokGrid.Companion.DEFAULT_SIZE
import rule.OmokRule
import rule.WhiteRenjuRule
import rule.type.Violation
import rule.wrapper.point.Point

class WhitePlayer(width: Int = DEFAULT_SIZE, height: Int = DEFAULT_SIZE) : Player() {
    override val stonesBacking: MutableList<Point> = mutableListOf()
    override val rule: OmokRule = WhiteRenjuRule(width, height)

    override fun validateRenjuRule(
        otherStones: List<Point>,
        startPoint: Point,
    ) {
        dealViolation(Violation.NONE)
    }
}
