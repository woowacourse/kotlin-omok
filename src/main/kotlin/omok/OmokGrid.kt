package omok

class OmokGrid {
    val board: List<MutableList<StoneState>>

    init {
        board =
            List(DEFAULT_SIZE) { row ->
                MutableList(DEFAULT_SIZE) { col ->
                    StoneState.BLANK
                }
            }
    }

    fun putStone(
        row: Int,
        col: Int,
        state: StoneState,
    ) {
        if (board[row][col] != StoneState.BLANK) throw IllegalStateException(ERROR_STONE_ALREADY_PUT)
        board[row][col] = state
    }

    companion object {
        private const val DEFAULT_SIZE: Int = 15
        private const val ERROR_STONE_ALREADY_PUT = "이미 돌이 있습니다."
    }
}
