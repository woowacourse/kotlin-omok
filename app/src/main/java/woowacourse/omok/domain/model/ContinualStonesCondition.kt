package woowacourse.omok.domain.model

import woowacourse.omok.domain.model.rule.ContinualStonesStandard

enum class ContinualStonesCondition {
    EXACT,
    CAN_OVERLINE,
    ;

    fun overline(
        actualContinualCount: Int,
        standardContinualCount: ContinualStonesStandard,
    ): Boolean {
        return when (this) {
            EXACT -> (actualContinualCount > standardContinualCount.count)
            CAN_OVERLINE -> false
        }
    }

    fun win(
        actualContinualCount: Int,
        standardContinualCount: ContinualStonesStandard,
    ): Boolean {
        return when (this) {
            EXACT -> (actualContinualCount == standardContinualCount.count)
            CAN_OVERLINE -> (actualContinualCount >= standardContinualCount.count)
        }
    }
}
