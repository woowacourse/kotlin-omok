package woowacourse.omok.domain.model

enum class ContinualStonesCondition {
    STRICT, // 정확히 N 목일 때 우승, 장목은 금수임
    NAIVE_OVERLINE_AVAILABLE, // 정확히 N 목일 때 우승, 장목은 금수가 아님
    OVERLINE_AVAILABLE, // N 목 이상일 때 우승, 장목은 금수가 아님
}
