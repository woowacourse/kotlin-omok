package woowacourse.omok.model.board

@JvmInline
value class Y(
    val value: Int,
) {
    init {
        require(value in Y_MIN_RANGE..Y_MAX_RANGE) { "Y 좌표의 범위는 1에서 15까지이다." }
    }

    operator fun plus(other: Int) = Y(Y_MIN_RANGE + (this.value - Y_MIN_RANGE) + other)

    operator fun minus(other: Int) = Y(Y_MIN_RANGE + (this.value - Y_MIN_RANGE) - other)

    companion object {
        private const val Y_MIN_RANGE = 1
        private const val Y_MAX_RANGE = 15
    }
}
