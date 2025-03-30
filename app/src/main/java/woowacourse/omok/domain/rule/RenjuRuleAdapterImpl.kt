package woowacourse.omok.domain.rule

import rule.facade.BlackRenjuRule
import woowacourse.omok.domain.StoneColor
import woowacourse.omok.domain.grid.OmokGrid.Companion.DEFAULT_SIZE
import woowacourse.omok.domain.grid.Stone

object RenjuRuleAdapterImpl : OmokRuleAdapter() {
    private val rule = BlackRenjuRule(DEFAULT_SIZE, DEFAULT_SIZE)

    override fun checkViolation(
        thisStones: Set<Stone>,
        otherStones: Set<Stone>,
        latestStone: Stone,
    ): ValidationResult {
        if (latestStone.stoneColor == StoneColor.WHITE) return ValidationResult.Success

        val thisPoints = convertSetToList(thisStones)
        val otherPoints = convertSetToList(otherStones)
        val startPoint = convertOmokPointToPoint(latestStone)

        return when {
            rule.checkOverline(thisPoints, startPoint) -> ValidationResult.Failure.OverLine
            rule.checkDoubleFourFoul(thisPoints, otherPoints, startPoint) -> ValidationResult.Failure.DoubleFour
            rule.checkDoubleThreeFoul(thisPoints, otherPoints, startPoint) -> ValidationResult.Failure.DoubleThree
            else -> ValidationResult.Success
        }
    }

    private fun convertOmokPointToPoint(stone: Stone): Pair<Int, Int> {
        return Pair(stone.point.row.value, stone.point.col.value)
    }

    private fun convertSetToList(stones: Set<Stone>): List<Pair<Int, Int>> {
        return stones.toList().map { convertOmokPointToPoint(it) }
    }
}
