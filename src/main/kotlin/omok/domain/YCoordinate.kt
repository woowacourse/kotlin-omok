package omok.domain

@JvmInline
value class YCoordinate(val value: Int) {
    init {
        require(value in Y_MIN_RANGE..Y_MAX_RANGE) { ERROR_Y_COORDINATE_OUT_OF_RANGE }
    }

    companion object {
        private const val Y_MIN_RANGE = 1
        private const val Y_MAX_RANGE = 15
        private const val ERROR_Y_COORDINATE_OUT_OF_RANGE = "Y 좌표의 범위는 1부터 15까지 입니다."
    }
}
