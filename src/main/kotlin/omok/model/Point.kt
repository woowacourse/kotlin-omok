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
        private const val MIN_ROW = "A"
        private const val MAX_ROW = "O"
        private const val MIN_COL = 1
        private const val MAX_COL = 15
    }
}
