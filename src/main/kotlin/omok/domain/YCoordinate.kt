package omok.domain

data class YCoordinate(val value: Int) {
    init {
        require(value in Y_MIN_RANGE..Y_MAX_RANGE) { ERROR_Y_COORDINATE_OUT_OF_RANGE }
    }

    val isInEdge: Boolean = value == Y_MIN_RANGE || value == Y_MAX_RANGE

    operator fun plus(other: Int) = YCoordinate(value + other)

    operator fun minus(other: Int) = YCoordinate(value - other)

    companion object {
        private const val Y_MIN_RANGE = 1
        private const val Y_MAX_RANGE = 15
        private const val ERROR_Y_COORDINATE_OUT_OF_RANGE = "Y 좌표의 범위는 1부터 15까지 입니다."
        private val Y_COORDINATES = (Y_MIN_RANGE..Y_MAX_RANGE).map(::YCoordinate)
        fun all(): List<YCoordinate> = Y_COORDINATES
    }
}
