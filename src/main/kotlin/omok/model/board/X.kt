package omok.model.board

data class X(
    val point: Int,
) {
    init {
        require(point in 1..15) { "좌표의 범위는 1부터 15까지 입니다." }
    }

    val isLeftMost: Boolean = point == X_MIN_RANGE
    val isRightMost: Boolean = point == X_MAX_RANGE
    val isInEdge: Boolean = isLeftMost || isRightMost

    operator fun plus(other: Int) = X(X_MIN_RANGE + (this.point - X_MIN_RANGE) + other)

    operator fun minus(other: Int) = X(X_MIN_RANGE + (this.point - X_MIN_RANGE) - other)

    companion object {
        private const val X_MIN_RANGE = 1
        private const val X_MAX_RANGE = 15
    }
}
