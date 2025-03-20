package omok.model.board

data class Position(val x: Int, val y: Int) {
    init {
        require(listOf(x, y).all { it in Board.BOARD_MIN_SIZE..Board.BOARD_MAX_SIZE }) { INVALID_POSITION_MESSAGE }
    }

    companion object {
        private const val INVALID_POSITION_MESSAGE = "올바른 좌표 범위를 입력해주세요"
    }
}
