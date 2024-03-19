package omok.model

data class Coordinate(val row: String, val col: Int) {
    init {
        require(row in MIN_ROW..MAX_ROW && row.length == 1) {
            "행의 범위는 $MIN_ROW ~ $MAX_ROW 사이어야 합니다."
        }
        require(col in MIN_COL..MAX_COL) {
            "열의 범위는 $MIN_COL ~ $MAX_COL 사이어야 합니다."
        }
    }

    companion object {
        private const val MIN_ROW = "A"
        private const val MAX_ROW = "O"
        private const val MIN_COL = 1
        private const val MAX_COL = 15
    }
}
