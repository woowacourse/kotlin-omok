package omok.model

class Board {
    val table: List<List<StoneType>>
        get() = _board.toList()
    private val _board: MutableList<MutableList<StoneType>> =
        MutableList(BOARD_SIZE) {
            MutableList(BOARD_SIZE) { StoneType.EMPTY }
        }

    val turn: Turn
        get() = _turn
    private var _turn: Turn = BlackTurn()

    fun putStone(point: Point): Turn {
        val previousTurn = _turn
        _turn = turn.nextTurn(point, this)
        if (previousTurn != turn) _board[point.y][point.x] = turn.before!!.type
        return turn
    }

    operator fun contains(point: Point): Boolean {
        return table[point.y][point.x] != StoneType.EMPTY
    }

    companion object {
        private const val BOARD_SIZE = 15
    }
}
