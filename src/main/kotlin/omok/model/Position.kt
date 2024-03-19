package omok.model

data class Position(val row: Int, val col: Int) {
    init {
        require(row in INDEX_RANGE && col in INDEX_RANGE) { "올바르지 않은 위치입니다." }
    }

    companion object {
        private const val MIN_INDEX = 0
        private const val MAX_INDEX = 14
        val INDEX_RANGE = MIN_INDEX..MAX_INDEX
    }
}
