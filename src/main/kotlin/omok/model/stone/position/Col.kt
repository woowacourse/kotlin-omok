package omok.model.stone.position

@JvmInline
value class Col(
    val value: Int,
) {
    init {
        require(value >= MIN_RANGE) { ERROR_COL_RANGE }
    }

    companion object {
        private const val MIN_RANGE = 0
        private const val ERROR_COL_RANGE = "열의 좌표는 0보다 작을 수 없습니다"
    }
}
