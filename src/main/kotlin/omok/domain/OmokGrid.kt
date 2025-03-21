package omok.domain

import rule.wrapper.point.Point

class OmokGrid(val width: Int = DEFAULT_SIZE, val height: Int = DEFAULT_SIZE) {
    private val _board: List<MutableList<StoneState>> = List(width + 1) { MutableList(height + 1) { StoneState.BLANK } }
    val board: List<MutableList<StoneState>>
        get() = _board.deepCopy()

    fun validateEmptyPoint(point: Point) {
        if (_board[point.row][point.col] != StoneState.BLANK) throw IllegalStateException(ERROR_STONE_ALREADY_PUT)
    }

    fun putStone(
        point: Point,
        state: StoneState,
    ) {
        _board[point.row][point.col] = state
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
