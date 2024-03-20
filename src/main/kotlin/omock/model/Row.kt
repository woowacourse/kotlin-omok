package omock.model

@JvmInline
value class Row(val comma: String) {
    init {
        require(comma.toIntOrNull() != null) {
            ERROR_ROW_TYPE
        }
        require(comma in ROW_RANGE) {
            ERROR_ROW_RANGE
        }
    }

    fun getIndex(): Int {
        return comma.toInt() - MIN_ROW
    }

    companion object {
        const val MIN_ROW = 1
        const val MAX_ROW = 15
        const val MIN_ROW_INDEX = MIN_ROW - 1
        const val MAX_ROW_INDEX = MAX_ROW - 1
        val ROW_RANGE = (MAX_ROW downTo MIN_ROW).map { it.toString() }
        private const val ERROR_ROW_TYPE = "Row는 정수형이어야 합니다."
        private const val ERROR_ROW_RANGE = "Row는 ${MIN_ROW}~${MAX_ROW} 사이여야 합니다."
    }
}
