package omok.model

class Board {
    val table: List<List<StoneType>>
        get() = _table.toList()
    private val _table: List<MutableList<StoneType>> =
        List(BOARD_SIZE) {
            MutableList(BOARD_SIZE) { StoneType.EMPTY }
        }

    var turn: Turn = BlackTurn()
        private set

    fun putStone(point: Point): Turn {
        val previousTurn = turn
        turn = turn.nextTurn(point, this)
        if (previousTurn != turn) {
            _table[point.y][point.x] = turn.before!!.type
        }
        return turn
    }

    fun getPointStoneLine(x: Int): List<StoneType> = table[BOARD_SIZE - x]

    operator fun contains(point: Point): Boolean {
        return table[point.y][point.x] != StoneType.EMPTY
    }

    companion object {
        private const val BOARD_SIZE = 15
    }
}
