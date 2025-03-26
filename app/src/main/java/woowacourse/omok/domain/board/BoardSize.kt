package woowacourse.omok.domain.board

@JvmInline
value class BoardSize(
    val value: Int = DEFAULT_SIZE,
) {
    init {
        require(value in DEFAULT_SIZE..MAX_SIZE) { INVALID_BOARD_SIZE }
    }

    companion object {
        const val MIN_SIZE = 1
        const val DEFAULT_SIZE = 15
        private const val MAX_SIZE = 25
        const val INVALID_BOARD_SIZE = "유효하지 않은 바둑판 사이즈 입니다. (${DEFAULT_SIZE} ~ $MAX_SIZE)"
    }
}
