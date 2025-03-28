package woowacourse.omok.domain.rule

import woowacourse.omok.domain.rule.lib.CountInRowRule
import woowacourse.omok.domain.rule.lib.DoubleFourMoveRule
import woowacourse.omok.domain.rule.lib.DoubleThreeMoveRule
import woowacourse.omok.domain.rule.lib.OmokMoveRule
import woowacourse.omok.domain.rule.lib.OverlineRule

class OmokMoveRules(
    winningRules: List<OmokMoveRule> = emptyList(),
    violationRules: List<OmokMoveRule> = emptyList(),
) {
    val winningRules: List<OmokMoveRule> =
        winningRules
            .ifEmpty {
                mutableListOf(
                    CountInRowRule(OmokMoveRule.BLACK_STONE) { it == OMOK_COUNT },
                    CountInRowRule(OmokMoveRule.WHITE_STONE) { it >= OMOK_COUNT },
                )
            }.toMutableList()

    val violationRules: MutableList<OmokMoveRule> =
        violationRules
            .ifEmpty {
                mutableListOf(
                    DoubleThreeMoveRule(OmokMoveRule.BLACK_STONE),
                    DoubleFourMoveRule(OmokMoveRule.BLACK_STONE),
                    OverlineRule(OmokMoveRule.BLACK_STONE),
                )
            }.toMutableList()

    companion object {
        private const val OMOK_COUNT = 5
    }
}
