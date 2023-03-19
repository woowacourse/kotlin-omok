package domain.rule

data class XCoordinate(val value: Char) {
    init {
        require(value in X_MIN_RANGE..X_MAX_RANGE) { ERROR_X_COORDINATE_OUT_OF_RANGE }
    }

    val isLeftMost: Boolean = value == X_MIN_RANGE
    val isRightMost: Boolean = value == X_MAX_RANGE
    val isInEdge: Boolean = isLeftMost || isRightMost

    operator fun plus(other: Int) =
        XCoordinate(X_MIN_RANGE + (this.value - X_MIN_RANGE) + other)

    operator fun minus(other: Int) =
        XCoordinate(X_MIN_RANGE + (this.value - X_MIN_RANGE) - other)

    companion object {
        private const val X_MIN_RANGE = 'A'
        private const val X_MAX_RANGE = 'O'
        private const val ERROR_X_COORDINATE_OUT_OF_RANGE = "X 좌표의 범위는 A부터 O까지 입니다."
        private val X_COORDINATES = ('A'..'O').map(::XCoordinate)
        fun all(): List<XCoordinate> = X_COORDINATES
    }
}
