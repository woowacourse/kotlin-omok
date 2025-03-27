package woowacourse.omok.domain.state

sealed class PlaceResult {
    class Placed(
        val state: State,
    ) : PlaceResult()

    sealed class ForbiddenMove(
        open val message: String,
    ) : PlaceResult() {
        data class DoubleThree(
            override val message: String = ERROR_DOUBLE_THREE,
        ) : ForbiddenMove(message)

        data class DoubleFour(
            override val message: String = ERROR_DOUBLE_FOUR,
        ) : ForbiddenMove(message)

        data class Overline(
            override val message: String = ERROR_OVERLINE,
        ) : ForbiddenMove(message)

        data class Occupied(
            override val message: String = ERROR_OCCUPIED,
        ) : ForbiddenMove(message)

        data class OutOfBoard(
            override val message: String = ERROR_OUT_OF_BOARD,
        ) : ForbiddenMove(message)
    }

    companion object {
        private const val ERROR_DOUBLE_THREE = "3-3 위치에 놓을 수 없습니다."
        private const val ERROR_DOUBLE_FOUR = "4-4 위치에 놓을 수 없습니다."
        private const val ERROR_OVERLINE = "장목 위치에 놓을 수 없습니다."
        private const val ERROR_OCCUPIED = "이미 돌이 놓여져 있습니다."
        private const val ERROR_OUT_OF_BOARD = "오목판의 범위를 넘어간 좌표입니다."
    }
}
