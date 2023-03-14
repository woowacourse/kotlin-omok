package omok.domain

@JvmInline
value class YCoordinate(val value: Int) {
    init {
        require(value in 1..15) { ERROR_Y_COORDINATE_OUT_OF_RANGE }
    }

    companion object {
        private const val ERROR_Y_COORDINATE_OUT_OF_RANGE = "Y 좌표의 범위는 1부터 15까지 입니다."
    }
}
