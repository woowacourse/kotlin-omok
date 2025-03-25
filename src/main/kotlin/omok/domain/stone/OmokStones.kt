package omok.domain.stone

import omok.domain.board.OmokBoard

class OmokStones {
    var stones: List<Stone> = listOf()
        get() = field.toList()
        private set

    fun getEmptyStones(): List<Stone> {
        return (1..OmokBoard.MAX_ROW_SIZE).flatMap { y ->
            (1..OmokBoard.MAX_COLUMN_SIZE).map { x ->
                getPointAt(x, y)
            }
        }
    }

    fun getPointAt(
        row: Int,
        column: Int,
    ): Stone {
        val isValidRow = row in 1..OmokBoard.MAX_ROW_SIZE
        val isValidColumn = column in 1..OmokBoard.MAX_COLUMN_SIZE
        return stones.find { it.x == column && it.y == row }
            ?: if (isValidRow && isValidColumn) Empty(column, row) else Protected.dummy()
    }

    fun add(stone: Stone) {
        require(stone !is Empty) { ERROR_EMPTY_ADD_NOT_SUPPORTED }
        require(!isOccupied(stone)) { ERROR_OCCUPIED_POSITION }
        require(!isProtected(stone)) { ERROR_PROTECTED_POSITION }
        stones += stone
    }

    fun remove(stone: Stone) {
        stones.find { it.x == stone.x && it.y == stone.y }?.let { stones -= it }
    }

    private fun isOccupied(stone: Stone): Boolean {
        return getPointAt(stone.y, stone.x) !is Empty
    }

    private fun isProtected(stone: Stone): Boolean {
        if (stone is White) return false
        return getPointAt(stone.y, stone.x) is Protected
    }

    companion object {
        private const val ERROR_EMPTY_ADD_NOT_SUPPORTED = "빈 Stone은 넣을 수 없습니다"
        private const val ERROR_OCCUPIED_POSITION = "해당 위치에는 이미 돌이 놓여 있습니다. 다른 위치를 선택하세요."
        private const val ERROR_PROTECTED_POSITION = "해당 위치는 금수 자리입니다. 다른 위치를 선택하세요."
    }
}
