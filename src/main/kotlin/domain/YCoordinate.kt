package domain

class YCoordinate private constructor(val y: Int) {

    init {
        require(y in Y_MIN_RANGE..Y_MAX_RANGE) { Y_COORDINATE_RANGE_ERROR }
    }

    companion object {
        private const val Y_COORDINATE_RANGE_ERROR = "y 좌표는 1에서 15 사이의 숫자로 생성해야 합니다."
        const val Y_MIN_RANGE = 1
        const val Y_MAX_RANGE = 15
        private val COORDINATES: Map<Int, YCoordinate> = (Y_MIN_RANGE..Y_MAX_RANGE).associateWith { YCoordinate(it) }

        fun of(y: Int): YCoordinate = COORDINATES[y] ?: YCoordinate(y)
    }
}
