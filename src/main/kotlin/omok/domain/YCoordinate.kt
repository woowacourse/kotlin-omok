package omok.domain

import omok.domain.OmokBoard.Companion.Y_MAX_RANGE
import omok.domain.OmokBoard.Companion.Y_MIN_RANGE

data class YCoordinate(val value: Int) {
    init {
        require(value in Y_MIN_RANGE..Y_MAX_RANGE) { ERROR_Y_COORDINATE_OUT_OF_RANGE }
    }

    val isTopMost: Boolean = value == Y_MAX_RANGE
    val isBottomMost: Boolean = value == Y_MIN_RANGE
    val isInEdge: Boolean = isBottomMost || isTopMost

    operator fun plus(other: Int) = YCoordinate(value + other)

    operator fun minus(other: Int) = YCoordinate(value - other)

    companion object {
        private const val ERROR_Y_COORDINATE_OUT_OF_RANGE = "Y 좌표의 범위는 1부터 15까지 입니다."
    }
}
