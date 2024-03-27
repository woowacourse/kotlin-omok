package omok.model.position

data class Row(val value: Int) {
    constructor(rowValue: Char) : this(rowValue - X_AXIS_START) {
        require(value in rowRange) { invalidRowMessage(value) }
    }

    private fun invalidRowMessage(row: Int): String {
        val rowValue = X_AXIS_START + row
        return EXCEPTION_INVALID_ROW.format(rowValue)
    }

    companion object {
        const val X_AXIS_START = 'A'
        private const val MIN_RANGE = 0
        private const val MAX_RANGE = 14
        private const val EXCEPTION_INVALID_ROW = "유효하지 않은 x축입니다. 현재 입력 값: %s\n"
        private val rowRange = MIN_RANGE..MAX_RANGE
    }
}
