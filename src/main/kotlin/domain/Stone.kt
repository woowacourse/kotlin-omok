package domain

class Stone(x: Int, y: Int) {
    init {
        require(x in MIN_RANGE..MAX_RANGE && y in MIN_RANGE..MAX_RANGE) { ERROR_OUT_OF_RANGE }
    }

    companion object {
        private const val ERROR_OUT_OF_RANGE = "잘못된 위치입니다."
        private const val MIN_RANGE = 0
        private const val MAX_RANGE = 14
    }
}
