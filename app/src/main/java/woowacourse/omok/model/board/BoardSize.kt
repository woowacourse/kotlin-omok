package woowacourse.omok.model.board

@JvmInline
value class BoardSize(
    val value: Int = DEFAULT_BOARD_SIZE,
) {
    init {
        require(value >= OMOK_LENGTH) { ERROR_INVALID_BOARD_SIZE }
        require(value <= ALPHABET_LENGTH) { ERROR_INVALID_BOARD_SIZE }
    }

    companion object {
        private const val DEFAULT_BOARD_SIZE = 15
        private const val OMOK_LENGTH = 5
        private const val ALPHABET_LENGTH = 26

        private const val ERROR_INVALID_BOARD_SIZE =
            "허용되지 않는 보드 크기입니다 $OMOK_LENGTH~$ALPHABET_LENGTH 사이의 크기로 초기화 하세요"
    }
}
