package omok.domain


fun List<MutableList<StoneState>>.deepCopy(): List<MutableList<StoneState>> = map { it.toMutableList() }.toList()

class OmokGrid(val width: Int = DEFAULT_SIZE, val height: Int = DEFAULT_SIZE) {
    private val _board: List<MutableList<StoneState>> = List(width + 1) { MutableList(height + 1) { StoneState.BLANK } }
    val board: List<MutableList<StoneState>>
        get() = _board.deepCopy()

    fun canPlace(point: Position) {
        if (_board[point.y][point.x] != StoneState.BLANK) throw IllegalStateException(ERROR_STONE_ALREADY_PUT)
    }

    fun putStone(
        point: Position,
        state: StoneState,
    ) {
        _board[point.y][point.x] = state
    }

    fun isFull(): Boolean {
        return _board.all { row -> row.all { it != StoneState.BLANK } }
    }

    companion object {
        const val DEFAULT_SIZE: Int = 15
        private const val ERROR_STONE_ALREADY_PUT = "이미 돌이 있습니다."
    }
}
