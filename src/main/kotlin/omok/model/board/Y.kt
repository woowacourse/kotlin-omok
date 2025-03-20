package omok.model.board

data class Y(
    val point: Int,
) {
    init {
        require(point in 1..15) { "Y좌표는 1에서 15까지이다." }
    }

    private val isLeftMost: Boolean = point == Y_MIN_RANGE
    private val isRightMost: Boolean = point == Y_MAX_RANGE

    operator fun plus(other: Int) = Y(Y_MIN_RANGE + (this.point - Y_MIN_RANGE) + other)

    operator fun minus(other: Int) = Y(Y_MIN_RANGE + (this.point - Y_MIN_RANGE) - other)

    companion object {
        private const val Y_MIN_RANGE = 1
        private const val Y_MAX_RANGE = 15
    }
}
