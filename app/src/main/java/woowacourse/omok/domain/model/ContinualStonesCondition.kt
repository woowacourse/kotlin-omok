package woowacourse.omok.domain.model

import woowacourse.omok.domain.model.rule.ContinualStonesStandard

enum class ContinualStonesCondition {
    STRICT, // 정확히 N 목일 때 우승, 장목은 금수임
    NAIVE_OVERLINE_AVAILABLE, // 정확히 N 목일 때 우승, 장목은 금수가 아님
    OVERLINE_AVAILABLE, // N 목 이상일 때 우승, 장목은 금수가 아님
    ;

    fun overline(
        actualContinualCount: Int,
        standardContinualCount: ContinualStonesStandard,
    ): Boolean {
        return when (this) {
            STRICT -> (actualContinualCount > standardContinualCount.count)
            NAIVE_OVERLINE_AVAILABLE -> false
            OVERLINE_AVAILABLE -> false
        }
    }

    fun win(
        actualContinualCount: Int,
        standardContinualCount: ContinualStonesStandard,
    ): Boolean {
        return when (this) {
            STRICT -> (actualContinualCount == standardContinualCount.count)
            NAIVE_OVERLINE_AVAILABLE -> (actualContinualCount == standardContinualCount.count)
            OVERLINE_AVAILABLE -> (actualContinualCount >= standardContinualCount.count)
        }
    }
}
