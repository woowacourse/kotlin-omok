package domain

class YCoordinate private constructor(y: Int) {

    init {
        require(y in 1..15) { Y_COORDINATE_RANGE_ERROR }
    }

    companion object {
        private const val Y_COORDINATE_RANGE_ERROR = "y 좌표는 1에서 15 사이의 숫자로 생성해야 합니다."
        private val COORDINATES: Map<Int, YCoordinate> = (1..15).associateWith { YCoordinate(it) }

        fun of(y: Int): YCoordinate = COORDINATES[y] ?: YCoordinate(y)
    }
}
