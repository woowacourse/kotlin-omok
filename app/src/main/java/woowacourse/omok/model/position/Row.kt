package woowacourse.omok.model.position

@JvmInline
value class Row(val coordinate: String) {
    init {
        require(coordinate.toIntOrNull() != null) {
            ERROR_ROW_TYPE
        }
        require(coordinate in ROW_RANGE) {
            ERROR_ROW_RANGE
        }
    }

    fun getIndex(): Int {
        return coordinate.toInt() - MIN_ROW
    }

    fun toBoardIndex(): Int {
        return MIN_ROW + MAX_ROW - coordinate.toInt()
    }

    companion object {
        const val MIN_ROW = 1
        const val MAX_ROW = 15
        const val MIN_ROW_INDEX = MIN_ROW - 1
        const val MAX_ROW_INDEX = MAX_ROW - 1
        val ROW_RANGE = (MIN_ROW..MAX_ROW).map { it.toString() }

        private const val ERROR_ROW_TYPE = "Row는 정수형이어야 합니다."
        private const val ERROR_ROW_RANGE = "Row는 $MIN_ROW~$MAX_ROW 사이여야 합니다."

        fun transformIndex(index: Int): String {
            return (MAX_ROW - index).toString()
        }
    }
}
