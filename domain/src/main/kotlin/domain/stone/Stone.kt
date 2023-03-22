package domain.stone

import domain.Board.Companion.BOARD_SIZE

abstract class Stone(val x: Int, val y: Int) {

    init {
        require(x in 0 until BOARD_SIZE && y in 0 until BOARD_SIZE) { ERROR_OUT_OF_RANGE }
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Stone) return false
        return (other.x == this.x && other.y == this.y)
    }

    override fun hashCode(): Int {
        return 1
    }

    companion object {
        const val ERROR_OUT_OF_RANGE = "범위를 넘어가는 좌표를 생성할 수 없습니다."
    }
}
