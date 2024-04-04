package woowacourse.omok.domain.model

data class Position(val row: Int, val col: Int) {
    init {
        require(isOnBoard(row, col)) { "올바르지 않은 위치입니다." }
    }

    private fun isOnBoard(
        row: Int,
        col: Int,
    ): Boolean = row in INDEX_RANGE && col in INDEX_RANGE

    companion object {
        const val MIN_INDEX = 0
        const val MAX_INDEX = 14
        val INDEX_RANGE = MIN_INDEX..MAX_INDEX
    }
}
