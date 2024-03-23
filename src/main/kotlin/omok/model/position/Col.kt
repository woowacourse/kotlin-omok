package omok.model.position

data class Col(val value: Int) {
    companion object {
        private const val MIN_RANGE = 0
        private const val MAX_RANGE = 14
        private const val EXCEPTION_INVALID_COLUMN = "유효하지 않은 y축 입니다. 현재 입력 값: %d\n"
        private val columnRange = MIN_RANGE..MAX_RANGE

        fun from(input: Int): Col {
            val value = input - 1
            invalidColumn(input)
            return Col(value)
        }

        private fun invalidColumn(value: Int) =
            require(value - 1 in columnRange) {
                EXCEPTION_INVALID_COLUMN.format(value)
            }
    }
}
