package omok.model.board

@JvmInline
value class BoardSize(
    val value: Int = DEFAULT_BOARD_SIZE,
) {
    init {
        require(value >= MINIMUM_BOARD_SIZE) { ERROR_INVALID_BOARD_SIZE }
    }

    companion object {
        private const val DEFAULT_BOARD_SIZE = 15
        private const val MINIMUM_BOARD_SIZE = 5

        private const val ERROR_INVALID_BOARD_SIZE = "허용되지 않는 보드 크기입니다"
    }
}
