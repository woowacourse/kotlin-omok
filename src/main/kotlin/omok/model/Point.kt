package omok.model

data class Point(val row: Int, val col: Int) {
    init {
        require(row in VALUE_RANGE) {
            "행의 범위는 $MIN_VALUE ~ $MAX_VALUE 사이어야 합니다."
        }
        require(col in VALUE_RANGE) {
            "열의 범위는 $MIN_VALUE ~ $MAX_VALUE 사이어야 합니다."
        }
    }

    companion object {
        private const val MIN_VALUE = -1
        private const val MAX_VALUE = 16
        private val VALUE_RANGE = MIN_VALUE..MAX_VALUE
    }
}
