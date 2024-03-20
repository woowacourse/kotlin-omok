package omok.model

data class Position(val row: Int, val col: Int) {
    init {
        require(isInBoard(row, col)) { "올바르지 않은 위치입니다." }
    }

    fun move(direction: Direction): Position? {
        if (!isInBoard(row + direction.row, col + direction.col)) return null
        return Position(row + direction.row, col + direction.col)
    }

    private fun isInBoard(row: Int, col: Int): Boolean = row in INDEX_RANGE && col in INDEX_RANGE

    companion object {
        const val MIN_INDEX = 0
        const val MAX_INDEX = 14
        val INDEX_RANGE = MIN_INDEX..MAX_INDEX
    }
}
