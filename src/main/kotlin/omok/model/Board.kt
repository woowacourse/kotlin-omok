package omok.model

class Board {
    val table: Array<Array<StoneType>>
        get() = _board
    private val _board: Array<Array<StoneType>> =
        Array(BOARD_SIZE) {
            Array(BOARD_SIZE) { StoneType.EMPTY }
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
