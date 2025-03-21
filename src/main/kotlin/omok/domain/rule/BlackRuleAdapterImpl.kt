package omok.domain.rule

import omok.domain.OmokViolation
import omok.domain.point.OmokPoint
import rule.BlackRenjuRule

object BlackRuleAdapterImpl : OmokRuleAdapter() {
    override val rule = BlackRenjuRule()

    override fun checkViolation(
        blackStones: Set<OmokPoint>,
        whiteStones: Set<OmokPoint>,
        latestPoint: OmokPoint,
    ): OmokViolation {
        val blackPoints = dataConverter.convertSetToList(blackStones)
        val whitePoints = dataConverter.convertSetToList(whiteStones)
        val startPoint = dataConverter.convertOmokPointToPoint(latestPoint)

        val result = rule.checkAnyFoulCondition(blackPoints, whitePoints, startPoint)
        return dataConverter.convertViolation(result)
    }
}
