class Row private constructor(
    val value: Int,
) {
    init {
        require(value in MIN_VALUE..MAX_VALUE) { ERROR_OUT_OF_BOUND }
    }

    fun isSame(other: Row): Boolean {
        return this.value == other.value
    }

    companion object {
        private const val MIN_VALUE = 1
        private const val MAX_VALUE = 15
        private const val ERROR_OUT_OF_BOUND = "입력한 행이 범위를 벗어났습니다."

        fun from(value: Int): Row = Row(value)
    }
}
