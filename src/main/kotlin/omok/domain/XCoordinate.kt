package omok.domain

data class XCoordinate(val value: Int) {
    val isLeftMost: Boolean = value == X_MIN_RANGE
    val isRightMost: Boolean = value == X_MAX_RANGE
    val isInEdge: Boolean = isLeftMost || isRightMost
    init {
        require(value in X_MIN_RANGE..X_MAX_RANGE) { ERROR_X_COORDINATE_OUT_OF_RANGE }
    }

    constructor(value: Char) : this(value - 'A' + 1)

    fun toChar(): Char = ('A' + value - 1)
    operator fun plus(other: Int) =
        XCoordinate(X_MIN_RANGE + (this.value - X_MIN_RANGE) + other)

    operator fun minus(other: Int) =
        XCoordinate(X_MIN_RANGE + (this.value - X_MIN_RANGE) - other)

    companion object {
        private const val X_MIN_RANGE = 1
        private const val X_MAX_RANGE = 15
        private const val ERROR_X_COORDINATE_OUT_OF_RANGE = "X 좌표의 범위는 A부터 O까지 입니다."
        private val X_COORDINATES = (X_MIN_RANGE..X_MAX_RANGE).map(::XCoordinate)
        fun all(): List<XCoordinate> = X_COORDINATES
    }
}
