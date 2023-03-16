package domain

import domain.constant.Constant.BOARD_SIZE

@JvmInline
value class Coordinate(val value: Int) {
    init {
        validateRange()
    }

    private fun validateRange() {
        require(value in COORDINATE_RANGE) { "잘못된 좌표값입니다. 현재 입력된 좌표: $value" }
    }

    companion object {
        private val COORDINATE_RANGE = 0 until BOARD_SIZE
    }
}
