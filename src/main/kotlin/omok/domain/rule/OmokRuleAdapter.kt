package omok.domain.rule

import omok.domain.OmokViolation
import omok.domain.point.OmokPoint
import rule.OmokRule

abstract class OmokRuleAdapter {
    protected val dataConverter = DataConverter()
    abstract val rule: OmokRule

    abstract fun checkViolation(
        blackStones: Set<OmokPoint>,
        whiteStones: Set<OmokPoint>,
        latestPoint: OmokPoint,
    ): OmokViolation

    fun isWin(
        stones: Set<OmokPoint>,
        latestPoint: OmokPoint,
    ): Boolean {
        val points = dataConverter.convertSetToList(stones)
        val startPoint = dataConverter.convertOmokPointToPoint(latestPoint)

        return rule.checkSerialSameStonesBiDirection(points, startPoint, WIN_STANDARD)
    }

    companion object {
        private const val WIN_STANDARD: Int = 5
    }
}
