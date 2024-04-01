package woowacourse.omok.domain.model

import woowacourse.omok.domain.rule.FourFourRule
import woowacourse.omok.domain.rule.MoreThanFiveRule
import woowacourse.omok.domain.rule.OmokRule
import woowacourse.omok.domain.rule.ThreeThreeRule

class RenjuRuleAdaptor(private val omokRules: List<OmokRule>) {
    fun isForbidden(
        board: Board,
        stone: Stone,
    ): Result {
        val boardConverted = board.convert()
        val point = stone.point
        omokRules.forEach { omokRule ->
            if (!omokRule.isValidPosition(boardConverted, point.x, point.y)) return generateRuleResult(omokRule)
        }
        return Result.Success
    }

    private fun generateRuleResult(rule: OmokRule): Result =
        when (rule) {
            is ThreeThreeRule -> Result.ThreeThree
            is FourFourRule -> Result.FourFour
            is MoreThanFiveRule -> Result.MoreThanFive
        }

    private fun Board.convert(): List<List<Int>> {
        return board.map { row ->
            row.map { stoneType ->
                when (stoneType) {
                    StoneType.BLACK -> 1
                    StoneType.WHITE -> 2
                    StoneType.EMPTY -> 0
                }
            }
        }
    }
}
