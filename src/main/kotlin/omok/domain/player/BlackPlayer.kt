package omok.domain.player

import omok.domain.OmokGrid.Companion.DEFAULT_SIZE
import rule.BlackRenjuRule
import rule.OmokRule
import rule.wrapper.point.Point

class BlackPlayer(width: Int = DEFAULT_SIZE, height: Int = DEFAULT_SIZE) : Player() {
    override val stonesBacking: MutableList<Point> = mutableListOf()
    override val rule: OmokRule = BlackRenjuRule(width, height)

    override fun validateRenjuRule(
        otherStones: List<Point>,
        startPoint: Point,
    ) {
        val violation = rule.checkAnyFoulCondition(stonesBacking, otherStones, startPoint)
        dealViolation(violation)
    }
}
