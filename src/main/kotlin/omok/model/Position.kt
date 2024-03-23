package omok.model

data class Position(val row: Int, val col: Int) {
    init {
        require(isOnBoard(row, col)) { INVALID_POSITION_MESSAGE }
    }

    fun move(direction: Direction): Position? {
        if (!isOnBoard(row + direction.row, col + direction.col)) return null
        return Position(row + direction.row, col + direction.col)
    }

    private fun isOnBoard(
        row: Int,
        col: Int,
    ): Boolean {
        return row in INDEX_RANGE && col in INDEX_RANGE
    }

    companion object {
        private const val INVALID_POSITION_MESSAGE = "올바르지 않은 위치입니다."
        const val MIN_INDEX = 0
        const val MAX_INDEX = 14
        val INDEX_RANGE = MIN_INDEX..MAX_INDEX
    }
}
