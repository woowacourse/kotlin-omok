data class Position(val row: Int, val col: Int) {
    init {
        require(row in POSITION_RANGE) { ROW_OUT_OF_RANGE_ERROR_MESSAGE }
        require(col in POSITION_RANGE) { COLUMN_OUT_OF_RANGE_ERROR_MESSAGE }
    }

    companion object {
        private const val MIN_BOUND = 1
        private const val MAX_BOUND = 15
        val POSITION_RANGE = (MIN_BOUND..MAX_BOUND)
        private const val ROW_OUT_OF_RANGE_ERROR_MESSAGE = "행의 범위는 ${MIN_BOUND}부터 ${MAX_BOUND}입니다"
        private const val COLUMN_OUT_OF_RANGE_ERROR_MESSAGE = "열의 범위는 ${MIN_BOUND}부터 ${MAX_BOUND}입니다"
    }
}
