package omok.model.stone.position

@JvmInline
value class Row(
    val value: Int,
) {
    init {
        require(value >= MIN_RANGE) { ERROR_ROW_RANGE }
    }

    companion object {
        private const val MIN_RANGE = 0
        private const val ERROR_ROW_RANGE = "행의 좌표는 0보다 작을 수 없습니다"
    }
}
