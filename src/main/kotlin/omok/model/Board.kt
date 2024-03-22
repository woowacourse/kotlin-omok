package omok.model

class Board {
    val table: Array<Array<StoneType>>
        get() = _table
    private val _table: Array<Array<StoneType>> =
        Array(BOARD_SIZE) {
            Array(BOARD_SIZE) { StoneType.EMPTY }
        }

    val turn: Turn
        get() = _turn
    private var _turn: Turn = BlackTurn()

    fun putStone(point: Point): Turn {
        val previousTurn = _turn
        _turn = turn.nextTurn(point, this)
        if (previousTurn != turn) _table[point.y][point.x] = turn.before!!.type
        return turn
    }

    fun getPointStoneLine(x: Int): Array<StoneType> = table[BOARD_SIZE - x]

    operator fun contains(point: Point): Boolean {
        return table[point.y][point.x] != StoneType.EMPTY
    }

    companion object {
        private const val BOARD_SIZE = 15
    }
}
