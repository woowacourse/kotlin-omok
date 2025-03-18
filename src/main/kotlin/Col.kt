class Col private constructor(
    val value: Int,
) {
    init {
        require(value in MIN_VALUE..MAX_VALUE) { ERROR_OUT_OF_BOUND }
    }

    companion object {
        private const val MIN_VALUE = 1
        private const val MAX_VALUE = 15
        private const val ERROR_OUT_OF_BOUND = "입력한 열이 범위를 벗어났습니다."
        private const val ASCII_A_OFFSET = 64

        fun from(char: Char): Col = Col(char.toGridCol())

        private fun Char.toGridCol(): Int = this.code - ASCII_A_OFFSET
    }
}
