package omok.model

class Board(val size: Int) {
    private val table: List<MutableList<StoneType>> =
        List(size) {
            MutableList(size) { StoneType.EMPTY }
        }

    var beforePoint: Point? = null
        private set

    fun putStone(
        point: Point,
        turn: Turn,
    ): Turn {
        val nextTurn = turn.nextTurn(point, RuleAdapter(this))
        if (turn != nextTurn) {
            beforePoint = point
            table[point.y][point.x] = turn.stoneType
        }
        return nextTurn
    }

    fun getBoardLine(index: Int): List<StoneType> = table[size - index]

    fun getBoardPoint(
        y: Int,
        x: Int,
    ): StoneType = table[y][x]

    operator fun contains(point: Point): Boolean {
        return table[point.y][point.x] != StoneType.EMPTY
    }
}
