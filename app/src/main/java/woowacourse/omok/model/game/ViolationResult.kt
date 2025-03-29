package woowacourse.omok.model.game

sealed class ViolationResult(
    open val message: String,
) {
    sealed class FoulConditionResult(
        message: String,
    ) : ViolationResult(message) {
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

    sealed class InvalidMoveResult(
        override val message: String,
        open val finish: Boolean,
    ) : ViolationResult(message) {
        data class OutOfBoard(
            override val message: String = "오목판 밖에 착수할 수 없습니다.",
            override val finish: Boolean = false,
        ) : InvalidMoveResult(message, finish)

        data class OccupiedPoint(
            override val message: String = "이미 돌이 있습니다.",
            override val finish: Boolean = false,
        ) : InvalidMoveResult(message, finish)

        data class FullBoard(
            override val message: String = "오목판이 가득차 있습니다.",
            override val finish: Boolean = true,
        ) : InvalidMoveResult(message, finish)
    }
}
