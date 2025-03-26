package woowacourse.omok.model.board

data class X(
    val point: Int,
) {
    init {
        require(point in X_MIN_RANGE..X_MAX_RANGE) { "좌표의 범위는 1부터 15까지 입니다." }
    }

    operator fun plus(other: Int) = X(X_MIN_RANGE + (this.point - X_MIN_RANGE) + other)

    operator fun minus(other: Int) = X(X_MIN_RANGE + (this.point - X_MIN_RANGE) - other)

    companion object {
        private const val X_MIN_RANGE = 1
        private const val X_MAX_RANGE = 15
    }
}
