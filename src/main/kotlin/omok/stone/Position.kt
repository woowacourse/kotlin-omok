package omok.stone

data class Position(val x: Int, val y: Int) {
    init {
        require(listOf(x, y).all { it in MIN_POSITION_RANGE..MAX_POSITION_RANGE }) { INVALID_POSITION_MESSAGE }
    }

    companion object {
        private const val MIN_POSITION_RANGE = 1
        private const val MAX_POSITION_RANGE = 15
        private const val INVALID_POSITION_MESSAGE = "올바른 좌표 범위를 입력해주세요"
    }
}
