package woowacourse.omok.domain.rule

import rule.facade.BlackRenjuRule
import woowacourse.omok.domain.StoneColor
import woowacourse.omok.domain.grid.OmokGrid.Companion.DEFAULT_SIZE
import woowacourse.omok.domain.grid.OmokPoint

object RenjuRuleAdapterImpl : OmokRuleAdapter() {
    private val rule = BlackRenjuRule(DEFAULT_SIZE, DEFAULT_SIZE)

    override fun checkViolation(
        thisStones: Set<OmokPoint>,
        otherStones: Set<OmokPoint>,
        latestPoint: OmokPoint,
    ): OmokViolation {
        if (latestPoint.stoneColor == StoneColor.WHITE) return OmokViolation.NONE

        val thisPoints = convertSetToList(thisStones)
        val otherPoints = convertSetToList(otherStones)
        val startPoint = convertOmokPointToPoint(latestPoint)

        return when {
            rule.checkOverline(thisPoints, startPoint) -> OmokViolation.OVER_LINE
            rule.checkDoubleThreeFoul(thisPoints, otherPoints, startPoint) -> OmokViolation.DOUBLE_THREE
            rule.checkDoubleFourFoul(thisPoints, otherPoints, startPoint) -> OmokViolation.DOUBLE_FOUR
            else -> OmokViolation.NONE
        }
    }

    private fun convertOmokPointToPoint(omokPoint: OmokPoint): Pair<Int, Int> {
        return Pair(omokPoint.point.row.value, omokPoint.point.col.value)
    }

    private fun convertSetToList(stones: Set<OmokPoint>): List<Pair<Int, Int>> {
        return stones.toList().map { convertOmokPointToPoint(it) }
    }
}
