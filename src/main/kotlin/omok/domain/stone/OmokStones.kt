package omok.domain.stone

import omok.domain.board.OmokBoard

class OmokStones {
    var stones: List<Stone> = listOf()
        get() = field.toList()
        private set

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
        stones += stone
    }

    fun isOccupied(stone: Stone): Boolean {
        return getPointAt(stone.y, stone.x) !is Empty
    }

    fun isProtected(stone: Stone): Boolean {
        if (stone is White) return false
        return getPointAt(stone.y, stone.x) is Protected
    }
}
