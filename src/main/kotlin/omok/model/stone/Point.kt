package omok.model.stone

data class Point(
    val row: Int,
    val col: Int,
) {
    init {
        require(row in MIN_BOARD_HEIGHT..MAX_BOARD_HEIGHT && col in MIN_BOARD_WIDTH..MAX_BOARD_WIDTH) { ERROR_MESSAGE_OUT_OF_BOARD }
    }

    companion object {
        private const val MIN_BOARD_WIDTH = 1
        private const val MIN_BOARD_HEIGHT = 1
        private const val MAX_BOARD_WIDTH = 15
        private const val MAX_BOARD_HEIGHT = 15
        private const val ERROR_MESSAGE_OUT_OF_BOARD = "오목판 범위를 벗어났습니다."
    }
}
