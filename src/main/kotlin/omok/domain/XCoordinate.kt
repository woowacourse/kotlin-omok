package omok.domain

@JvmInline
value class XCoordinate(val value: Char) {
    init {
        require(value in X_MIN_RANGE..X_MAX_RANGE) { ERROR_X_COORDINATE_OUT_OF_RANGE }
    }

    companion object {
        private const val X_MIN_RANGE = 'A'
        private const val X_MAX_RANGE = 'O'
        private const val ERROR_X_COORDINATE_OUT_OF_RANGE = "X 좌표의 범위는 A부터 O까지 입니다."
    }
}
