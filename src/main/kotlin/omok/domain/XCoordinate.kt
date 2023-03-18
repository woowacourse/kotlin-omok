package omok.domain

import omok.configure.Constants.X_MAX_RANGE
import omok.configure.Constants.X_MIN_RANGE

data class XCoordinate(val value: Int) {
    init {
        require(value in X_MIN_RANGE..X_MAX_RANGE) { ERROR_X_COORDINATE_OUT_OF_RANGE }
    }

    constructor(value: Char) : this(value - 'A' + 1)

    fun toChar(): Char = ('A' + value - 1)

    operator fun plus(other: Int) = value + other

    operator fun minus(other: Int) = value + other

    companion object {
        private const val ERROR_X_COORDINATE_OUT_OF_RANGE = "X 좌표의 범위는 A부터 O까지 입니다."
    }
}
