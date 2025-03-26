package woowacourse.omok.model.game

sealed class InvalidMoveResult(
    open val message: String,
    open val finish: Boolean,
) {
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
