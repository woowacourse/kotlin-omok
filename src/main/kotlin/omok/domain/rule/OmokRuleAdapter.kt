package omok.domain.rule

import omok.domain.point.OmokPoint
import rule.OmokRule
import rule.type.Violation

abstract class OmokRuleAdapter {
    protected val dataConverter = DataConverter()
    abstract val rule: OmokRule

    abstract fun checkViolation(
        blackStones: Set<OmokPoint>,
        whiteStones: Set<OmokPoint>,
        latestPoint: OmokPoint,
    ): Violation

    fun isWin(
        stones: Set<OmokPoint>,
        latestPoint: OmokPoint,
        winStandard: Int,
    ): Boolean {
        val points = dataConverter.convertSetToList(stones)
        val startPoint = dataConverter.convertOmokPointToPoint(latestPoint)

        return rule.checkSerialSameStonesBiDirection(points, startPoint, winStandard)
    }
}
