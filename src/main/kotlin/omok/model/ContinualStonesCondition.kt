package omok.model

import omok.model.rule.ContinualStonesStandard

enum class ContinualStonesCondition {
    EXACT,
    CAN_OVERLINE,
    ;

    fun map(
        actualContinualCount: Int,
        standardContinualCount: ContinualStonesStandard,
    ): Boolean {
        return when (this) {
            EXACT -> (actualContinualCount == standardContinualCount.count)
            CAN_OVERLINE -> (actualContinualCount >= standardContinualCount.count)
        }
    }
}
