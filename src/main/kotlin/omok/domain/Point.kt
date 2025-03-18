package omok.domain

class Point(
    val x: Int,
    val y: Int,
) {
    init {
        require(x in MIN_POSITION..MAX_POSITION) { ERROR_INVALID_POSITION }
        require(y in MIN_POSITION..MAX_POSITION) { ERROR_INVALID_POSITION }
    }

    companion object {
        private const val MIN_POSITION = 0
        private const val MAX_POSITION = 14
        private const val ERROR_INVALID_POSITION = "[ERROR] 바둑판의 크기는 15x15입니다."
    }
}
