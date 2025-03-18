package omok.domain

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
        position: Position,
        state: StoneState,
    ) {
        if (board[position.row][position.col] != StoneState.BLANK) throw IllegalStateException(ERROR_STONE_ALREADY_PUT)
        board[position.row][position.col] = state
    }

    companion object {
        const val DEFAULT_SIZE: Int = 15
        private const val ERROR_STONE_ALREADY_PUT = "이미 돌이 있습니다."
    }
}
