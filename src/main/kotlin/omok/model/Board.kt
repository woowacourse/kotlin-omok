package omok.model

class Board(val size: Int) {
    val table: List<List<StoneType>>
        get() = _table.toList()
    private val _table: List<MutableList<StoneType>> =
        List(size) {
            MutableList(size) { StoneType.EMPTY }
        }

    val beforePoint: Point?
        get() = _beforePoint
    private var _beforePoint: Point? = null

    fun putStone(
        point: Point,
        turn: Turn,
    ): Turn {
        val nextTurn = turn.nextTurn(point, this)
        if (turn != nextTurn) {
            _beforePoint = point
            _table[point.y][point.x] = turn.stoneType
        }
        return nextTurn
    }

    fun getPointStoneLine(x: Int): List<StoneType> = table[size - x]

    operator fun contains(point: Point): Boolean {
        return table[point.y][point.x] != StoneType.EMPTY
    }
}
