package domain.stone

import domain.BOARD_SIZE
import domain.MIN_VIEW_X
import domain.MIN_VIEW_Y

data class Point(val x: Int, val y: Int) {

    init {
        require(x in 0 until BOARD_SIZE && y in 0 until BOARD_SIZE) { ERROR_OUT_OF_RANGE }
    }

    companion object {
        const val ERROR_OUT_OF_RANGE = "범위를 넘어가는 좌표를 생성할 수 없습니다."
    }
}