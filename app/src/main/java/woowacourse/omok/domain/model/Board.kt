package woowacourse.omok.domain.model

class Board(val size: Int) {
    private val table: List<MutableList<StoneType>> =
        List(size) {
            MutableList(size) { StoneType.EMPTY }
        }

    var beforeStone: Stone? = null
        private set

    fun putStone(
        stone: Stone,
        turn: Turn,
        ruleAdapter: RuleAdapter,
    ): Turn {
        val nextTurn = turn.nextTurn(stone, ruleAdapter)
        if (turn != nextTurn) {
            beforeStone = stone
            table[stone.point.y][stone.point.x] = turn.stoneType
        }
        return nextTurn
    }

    fun getBoardLine(index: Int): List<StoneType> = table[size - index]

    fun getBoardPoint(
        point: Point
    ): StoneType = table[point.y][point.x]

    operator fun contains(point: Point): Boolean {
        return table[point.y][point.x] != StoneType.EMPTY
    }
}
