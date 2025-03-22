package omok.domain.rule

import omok.domain.OmokViolation
import omok.domain.point.OmokPoint

object BlackRuleAdapterImpl : OmokRuleAdapter() {
    override fun checkViolation(
        blackStones: Set<OmokPoint>,
        whiteStones: Set<OmokPoint>,
        latestPoint: OmokPoint,
    ): OmokViolation {
        val blackPoints = dataConverter.convertSetToList(blackStones)
        val whitePoints = dataConverter.convertSetToList(whiteStones)
        val startPoint = dataConverter.convertOmokPointToPoint(latestPoint)

        return when {
            rule.checkOverline(blackPoints, startPoint) -> OmokViolation.OVER_LINE
            rule.checkDoubleThreeFoul(blackPoints, whitePoints, startPoint) -> OmokViolation.DOUBLE_THREE
            rule.checkDoubleFourFoul(blackPoints, whitePoints, startPoint) -> OmokViolation.DOUBLE_FOUR
            else -> OmokViolation.NONE
        }
    }
}
