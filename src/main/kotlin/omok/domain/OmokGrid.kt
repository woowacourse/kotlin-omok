package omok.domain

class OmokGrid(board: List<MutableList<StoneState>> = List(DEFAULT_SIZE) { MutableList(DEFAULT_SIZE) { StoneState.BLANK } }) {
    private val _board: List<MutableList<StoneState>> = board.deepCopy()
    val board: List<MutableList<StoneState>>
        get() = _board.deepCopy()

    fun putStone(
        position: Position,
        state: StoneState,
    ) {
        if (_board[position.row][position.col] != StoneState.BLANK) throw IllegalStateException(ERROR_STONE_ALREADY_PUT)
        _board[position.row][position.col] = state
    }

    fun isFull(): Boolean {
        return _board.all { row -> row.all { it != StoneState.BLANK } }
    }

    companion object {
        const val DEFAULT_SIZE: Int = 15
        private const val ERROR_STONE_ALREADY_PUT = "이미 돌이 있습니다."
    }
}

fun List<MutableList<StoneState>>.deepCopy(): List<MutableList<StoneState>> = map { it.toMutableList() }.toList()
