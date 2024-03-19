package omock.model

@JvmInline
value class Row(val comma: String) {
    init {
        require(comma.toIntOrNull() != null) {
            ERROR_COLUMN_TYPE
        }
        require(comma in ROW_RANGE) {
            ERROR_COLUMN_RANGE
        }
    }

    fun getIndex(): Int {
        return comma.toInt() - 1
    }

    companion object {
        private const val MIN_COLUMN = 1
        private const val MAX_COLUMN = 15
        val ROW_RANGE = (MAX_COLUMN downTo MIN_COLUMN).map { it.toString() }
        private const val ERROR_COLUMN_TYPE = "Column은 정수형이어야 합니다."
        private const val ERROR_COLUMN_RANGE = "Column은 ${MIN_COLUMN}~${MAX_COLUMN} 사이여야 합니다."
    }
}
