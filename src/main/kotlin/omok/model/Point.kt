package omok.model

data class Point(val row: Int, val col: Int) {
    init {
        require(row in VALUE_RANGE) { ERROR_ROW_RANGE }
        require(col in VALUE_RANGE) { ERROR_COL_RANGE }
    }

    companion object {
        private const val MIN_VALUE = 0
        private const val MAX_VALUE = 14
        private val VALUE_RANGE = MIN_VALUE..MAX_VALUE

        private const val ERROR_ROW_RANGE = "행의 범위는 ${MIN_VALUE + 1} ~ ${MAX_VALUE + 1} 사이어야 합니다."
        private const val ERROR_COL_RANGE = "열의 범위는 ${MIN_VALUE + 1} ~ ${MAX_VALUE + 1} 사이어야 합니다."
    }
}
