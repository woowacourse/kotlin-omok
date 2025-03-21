package omok.domain.rule

import omok.domain.point.OmokPoint
import rule.BlackRenjuRule
import rule.type.Violation

object BlackRuleAdapterImpl : OmokRuleAdapter() {
    override val rule = BlackRenjuRule()

    override fun checkViolation(
        blackStones: Set<OmokPoint>,
        whiteStones: Set<OmokPoint>,
        latestPoint: OmokPoint,
    ): Violation {
        val blackPoints = dataConverter.convertSetToList(blackStones)
        val whitePoints = dataConverter.convertSetToList(whiteStones)
        val startPoint = dataConverter.convertOmokPointToPoint(latestPoint)

        return rule.checkAnyFoulCondition(blackPoints, whitePoints, startPoint)
    }
}
