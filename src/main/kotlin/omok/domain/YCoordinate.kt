package omok.domain

import omok.domain.OmokGame.Companion.BOARD_Y_SIZE

data class YCoordinate(val value: Int) {

    init {
        require(value in Y_MIN_RANGE..BOARD_Y_SIZE) { ERROR_Y_COORDINATE_OUT_OF_RANGE }
    }

    operator fun plus(other: Int) = YCoordinate(value + other)

    operator fun minus(other: Int) = YCoordinate(value - other)

    companion object {
        private const val Y_MIN_RANGE = 1
        private const val ERROR_Y_COORDINATE_OUT_OF_RANGE = "Y 좌표의 범위는 ${Y_MIN_RANGE}부터 ${BOARD_Y_SIZE}까지 입니다."
        fun createYLine(size: Int): List<YCoordinate> = (Y_MIN_RANGE..size).map(::YCoordinate)
    }
}
