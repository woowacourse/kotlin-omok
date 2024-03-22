package omok.model

data class Position(val row: Row, val col: Column) {
    companion object {
        private const val MIN_ROW = 1
        private const val MAX_ROW = 15
        private const val MIN_COL = 'A'
        private const val MAX_COL = 'O'
        private const val EXCEPTION_ROW_RANGE = "$MIN_ROW 부터 ${MAX_ROW}사이의 정수를 입력해야 합니다"
        private const val EXCEPTION_COL_RANGE = "$MIN_COL 부터 ${MAX_COL}사이의 알파벳을 입력해야 합니다"

        fun of(
            row: Int,
            col: Char,
        ): Position {
            require(row in MIN_ROW..MAX_ROW) { EXCEPTION_ROW_RANGE }
            require(col in MIN_COL..MAX_COL) { EXCEPTION_COL_RANGE }
            val rowValue = Row.valueOf(row) ?: throw IllegalArgumentException()
            val colValue = Column.titleOf(col) ?: throw IllegalArgumentException()
            return Position(rowValue, colValue)
        }
    }
}
