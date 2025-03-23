package omok.model.board

@JvmInline
value class BoardSize(val value: Int) {
    init {
        require(value in DEFAULT_SIZE..MAX_SIZE) { INVALID_BOARD_SIZE }
    }

    companion object {
        val DEFAULT = BoardSize(15)
        const val MIN_SIZE = 1
        private const val MAX_SIZE = 25
        private const val DEFAULT_SIZE = 15
        const val INVALID_BOARD_SIZE = "유효하지 않은 바둑판 사이즈 입니다. (${DEFAULT_SIZE} ~ $MAX_SIZE)"
    }
}
