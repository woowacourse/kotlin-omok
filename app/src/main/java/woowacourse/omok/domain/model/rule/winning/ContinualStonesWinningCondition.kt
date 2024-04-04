package woowacourse.omok.domain.model.rule.winning

import woowacourse.omok.domain.model.ContinualStonesCondition
import woowacourse.omok.domain.model.rule.ContinualStonesStandard
import woowacourse.omok.domain.model.rule.ContinualStonesStandard.Companion.MIN_CONTINUAL_STONES_COUNT

class ContinualStonesWinningCondition(
    private val continualStonesStandard: ContinualStonesStandard,
    private val continualStonesCondition: ContinualStonesCondition,
) {
    fun canHaveDoubleRule(): Boolean = continualStonesStandard > ContinualStonesStandard(MIN_CONTINUAL_STONES_COUNT)

    fun overline(actualContinualCount: Int): Boolean =
        when (continualStonesCondition) {
            ContinualStonesCondition.STRICT -> actualContinualCount > continualStonesStandard.count
            ContinualStonesCondition.NAIVE_OVERLINE_AVAILABLE -> false
            ContinualStonesCondition.OVERLINE_AVAILABLE -> false
        }

    fun win(actualContinualCount: Int): Boolean =
        when (continualStonesCondition) {
            ContinualStonesCondition.STRICT -> actualContinualCount == continualStonesStandard.count
            ContinualStonesCondition.NAIVE_OVERLINE_AVAILABLE -> actualContinualCount == continualStonesStandard.count
            ContinualStonesCondition.OVERLINE_AVAILABLE -> actualContinualCount >= continualStonesStandard.count
        }
}
