package omok.model.board

@JvmInline
value class BoardSize(val width: Int) {
    init {
        require(width >= MIN_SIZE) { "input: width - BoardSize 는 $MIN_SIZE 보다 커야함" }
    }

    fun isInBounds(
        x: Int,
        y: Int,
    ): Boolean {
        val range = MIN_SIZE..width
        return x in range && y in range
    }

    companion object {
        private const val MIN_SIZE = 1
    }
}
