package omok.model.board

@JvmInline
value class BoardSize(val width: Int) {
    val range: IntRange get() = MIN_SIZE..width

    companion object {
        private const val MIN_SIZE = 1
        private const val DEFAULT_BOARD_SIZE = 15
        val DEFAULT = BoardSize(DEFAULT_BOARD_SIZE)
    }
}
