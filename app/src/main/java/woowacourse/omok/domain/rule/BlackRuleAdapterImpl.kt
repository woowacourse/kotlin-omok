package woowacourse.omok.domain.rule

import rule.facade.BlackRenjuRule
import woowacourse.omok.domain.grid.OmokGrid.Companion.DEFAULT_SIZE
import woowacourse.omok.domain.grid.OmokPoint

object BlackRuleAdapterImpl : OmokRuleAdapter() {
    private val rule = BlackRenjuRule(DEFAULT_SIZE, DEFAULT_SIZE)

    override fun checkViolation(
        blackStones: Set<OmokPoint>,
        whiteStones: Set<OmokPoint>,
        latestPoint: OmokPoint,
    ): OmokViolation {
        val blackPoints = convertSetToList(blackStones)
        val whitePoints = convertSetToList(whiteStones)
        val startPoint = convertOmokPointToPoint(latestPoint)

        return when {
            rule.checkOverline(blackPoints, startPoint) -> OmokViolation.OVER_LINE
            rule.checkDoubleThreeFoul(blackPoints, whitePoints, startPoint) -> OmokViolation.DOUBLE_THREE
            rule.checkDoubleFourFoul(blackPoints, whitePoints, startPoint) -> OmokViolation.DOUBLE_FOUR
            else -> OmokViolation.NONE
        }
    }

    private fun convertOmokPointToPoint(omokPoint: OmokPoint): Pair<Int, Int> {
        return Pair(omokPoint.row.value, omokPoint.col.value)
    }

    private fun convertSetToList(stones: Set<OmokPoint>): List<Pair<Int, Int>> {
        return stones.toList().map { convertOmokPointToPoint(it) }
    }
}
