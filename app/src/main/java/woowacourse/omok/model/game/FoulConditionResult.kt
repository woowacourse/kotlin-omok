package woowacourse.omok.model.game

sealed class FoulConditionResult(
    open val message: String,
) {
    data class DoubleThree(
        override val message: String = "삼삼 금수입니다.",
    ) : FoulConditionResult(message)

    data class DoubleFour(
        override val message: String = "사사 금수입니다.",
    ) : FoulConditionResult(message)

    data class Overline(
        override val message: String = "장목 금수입니다.",
    ) : FoulConditionResult(message)
}
