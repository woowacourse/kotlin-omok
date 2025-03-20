package omok.domain.player

import rule.BlackRenjuRule
import rule.OmokRule
import rule.wrapper.point.Point

class BlackPlayer(width: Int, height: Int) : Player() {
    override val stonesBacking: MutableList<Point> = mutableListOf()
    override val rule: OmokRule = BlackRenjuRule(width, height)

    override fun isViolation(
        otherStones: List<Point>,
        startPoint: Point,
    ) {
        val violation = rule.checkAnyFoulCondition(stonesBacking, otherStones, startPoint)
        dealViolation(violation)
    }
}
