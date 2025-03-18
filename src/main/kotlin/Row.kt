class Row(
    val value: Int
) {
    init {
        require(value in MIN_VALUE..MAX_VALUE) { ERROR_OUT_OF_BOUND }
    }

    companion object {
        private const val MIN_VALUE = 1
        private const val MAX_VALUE = 15
        private const val ERROR_OUT_OF_BOUND = "입력한 행이 범위를 벗어났습니다."
    }

}