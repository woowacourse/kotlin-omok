package domain.stone

import domain.Board

data class Point(val x: Int, val y: Int) {

    init {
        require(x in 0 until Board.BOARD_SIZE && y in 0 until Board.BOARD_SIZE) { ERROR_OUT_OF_RANGE }
    }


    companion object {
        const val ERROR_OUT_OF_RANGE = "범위를 넘어가는 좌표를 생성할 수 없습니다."
    }
}