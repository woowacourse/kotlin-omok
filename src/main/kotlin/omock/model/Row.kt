package omock.model

@JvmInline
value class Row(val comma: String) {
    init {
        require(comma.toIntOrNull() != null) {
            ERROR_COLUMN_TYPE
        }
        require(comma in COLUM_RANGE) {
            ERROR_COLUMN_RANGE
        }
    }

    companion object {
        private const val MIN_COLUMN = 1
        private const val MAX_COLUMN = 15
        val COLUM_RANGE = (MIN_COLUMN..MAX_COLUMN).map { it.toString() }
        private const val ERROR_COLUMN_TYPE = "Column은 정수형이어야 합니다."
        private const val ERROR_COLUMN_RANGE = "Column은 ${MIN_COLUMN}~${MAX_COLUMN} 사이여야 합니다."
    }
}
