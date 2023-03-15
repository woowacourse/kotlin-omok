package domain

class XCoordinate private constructor(val x: Char) {

    init {
        require(x in X_MIN_RANGE..X_MAX_RANGE) { X_COORDINATE_RANGE_ERROR }
    }

    companion object {
        private const val X_COORDINATE_RANGE_ERROR = "x 좌표는 A에서 O 사이의 문자로 생성해야 합니다."
        const val X_MIN_RANGE = 'A'
        const val X_MAX_RANGE = 'O'
        private val COORDINATES: Map<Char, XCoordinate> = (X_MIN_RANGE..X_MAX_RANGE).associateWith { XCoordinate(it) }

        fun of(x: Char): XCoordinate {
            val upperX = x.uppercase()[0]
            return COORDINATES[upperX] ?: XCoordinate(upperX)
        }
    }
}
