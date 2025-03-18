package omok

class OmokGrid {
    val board: List<List<Point>>

    init {
        board =
            List(DEFAULT_SIZE) { row ->
                List(DEFAULT_SIZE) { col ->
                    Point(row + 1, col + 1, StoneState.BLANK)
                }
            }
    }

    fun putStone(
        row: Int,
        col: Int,
        state: StoneState,
    ) {
        if (board[row][col].state != StoneState.BLANK) throw IllegalStateException(ERROR_STONE_ALREADY_PUT)
        board[row][col].state = state
    }

    companion object {
        private const val DEFAULT_SIZE: Int = 15
        private const val ERROR_STONE_ALREADY_PUT = "이미 돌이 있습니다."
    }
}
