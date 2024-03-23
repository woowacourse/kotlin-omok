package omok.model.board

@JvmInline
value class BoardSize(val width: Int) {
    init {
        require(width >= MIN_SIZE) { "input: width - BoardSize 는 $MIN_SIZE 보다 커야함" }
    }

    fun range(): IntRange = MIN_RANGE..width

    fun isInBounds(
        x: Int,
        y: Int,
    ): Boolean {
        val range = MIN_RANGE..width
        return x in range && y in range
    }

    companion object {
        private const val MIN_RANGE = 1
        private const val MIN_SIZE = 5
    }
}
