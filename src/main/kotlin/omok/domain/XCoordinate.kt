package omok.domain

import omok.domain.OmokGame.Companion.BOARD_X_SIZE

data class XCoordinate(val value: Int) {

    init {
        require(value in X_MIN_RANGE..BOARD_X_SIZE) { ERROR_X_COORDINATE_OUT_OF_RANGE }
    }

    constructor(value: Char) : this(value - 'A' + 1)

    fun toChar(): Char = ('A' + value - 1)

    operator fun plus(other: Int) = XCoordinate(value + other)

    operator fun minus(other: Int) = XCoordinate(value - other)

    companion object {
        private const val X_MIN_RANGE = 1
        private const val ERROR_X_COORDINATE_OUT_OF_RANGE = "X 좌표의 범위는 ${'A' + X_MIN_RANGE - 1}부터 ${'A' + BOARD_X_SIZE - 1}까지 입니다."
        fun createXLine(size: Int): List<XCoordinate> = (X_MIN_RANGE..size).map(::XCoordinate)
    }
}
