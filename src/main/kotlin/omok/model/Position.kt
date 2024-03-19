package omok.model

data class Position(val row: Int, val col: Int) {
    init {
        require(row in INDEX_RANGE && col in INDEX_RANGE) { "올바르지 않은 위치입니다." }
    }

    fun move(direction: Direction) = Position(row + direction.row, col + direction.col)

    companion object {
        const val MIN_INDEX = 0
        const val MAX_INDEX = 14
        val INDEX_RANGE = MIN_INDEX..MAX_INDEX
    }
}
