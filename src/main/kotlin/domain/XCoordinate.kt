package domain

class XCoordinate private constructor(x: Char) {

    init {
        require(x in 'A'..'O') { X_COORDINATE_RANGE_ERROR }
    }

    companion object {
        private const val X_COORDINATE_RANGE_ERROR = "x 좌표는 A에서 O 사이의 문자로 생성해야 합니다."
        private val COORDINATES: Map<Char, XCoordinate> = ('A'..'O').associateWith { XCoordinate(it) }

        fun of(x: Char): XCoordinate {
            val upperX = x.uppercase()[0]
            return COORDINATES[upperX] ?: XCoordinate(upperX)
        }
    }
}
