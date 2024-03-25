package omok.model

class Board(val stones: Stones = Stones()) {
    private var _customBoard = Array(BOARD_SIZE) { Array(BOARD_SIZE) { 0 } }
    val customBoard: Array<Array<Int>>
        get() = _customBoard.clone()

    fun add(stone: Stone) {
        if (!stones.add(stone)) throw IllegalStateException(ERROR_DUPLICATED_POINT)
        updateCustomBoard(stone)
    }

    fun pointEmpty(point: Point): Boolean = !stones.occupied(point)

    fun lastStone(): Stone? {
        return stones.lastStone()
    }

    private fun updateCustomBoard(stone: Stone) {
        _customBoard[stone.point.row][stone.point.col] =
            (if (stone.color.isWhite()) 2 else 1)
    }

    companion object {
        private const val ERROR_DUPLICATED_POINT = "이미 수가 놓여진 곳에는 수를 놓으실 수 없습니다. 다른 위치에 수를 놓아주세요."

        private const val BOARD_SIZE = 15

        fun getSize(): Int = BOARD_SIZE
    }
}
