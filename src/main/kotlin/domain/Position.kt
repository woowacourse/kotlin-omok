package domain

import domain.domain.constant.DomainConstant.BOARD_SIZE

data class Position(val y: Int, val x: Int) {
    init {
        validateRange(y)
        validateRange(x)
    }

    private fun validateRange(n: Int) {
        if (n !in 0 until BOARD_SIZE) throw IllegalArgumentException("0~14 사이의 값만 가능합니다.")
    }
}
