package domain

class Stone(val row: Int, val column: Int) {
    init {
        require(row in MIN_RANGE..MAX_RANGE && column in MIN_RANGE..MAX_RANGE) { ERROR_OUT_OF_RANGE }
    }

    companion object {
        private const val ERROR_OUT_OF_RANGE = "잘못된 위치입니다."
        private const val MIN_RANGE = 0
        private const val MAX_RANGE = 14

        fun create(alphabet: String, number: Int): Stone {
            return Stone(15 - number, alphabet.toInt() - 'A'.code)
        }
    }
}
