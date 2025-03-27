package omok.domain

class Board(
    private val stones: Map<Position, StoneType> = emptyMap(),
    private val rule: Rule = RenjuRule(),
    private val size: Int = DEFAULT_SIZE,
) {
    val grid: Array<Array<StoneType>> = emptyArray()
    val lastMove: Position?
        get() = stones.keys.lastOrNull()

    fun getStoneAt(pos: Position): StoneType = stones[pos] ?: StoneType.EMPTY

    fun placeStone(
        position: Position,
        color: StoneType,
    ): Board {
        require(isPositionValid(position)) { INVALID_POSITION }
        require(isEmpty(position)) { ALREADY_PLACED }
        require(rule.isValidMove(this, position, color)) { INVALID_PLACED }

        return Board(stones + (position to color), rule, size)
    }

    private fun isPositionValid(position: Position) = position.x in 0 until size && position.y in 0 until size

    private fun isEmpty(position: Position) = !stones.containsKey(position)

    fun isOmok(stone: Stone): Boolean {
        TODO("Not yet implemented")
    }

    fun put(stone: Stone) {
        TODO("Not yet implemented")
    }

    companion object {
        const val DEFAULT_SIZE = 15
        private const val INVALID_POSITION = "위치가 보드 범위를 벗어났습니다. 유효하지 않은 위치 입니다."
        private const val INVALID_PLACED = "놓을 수 없는 위치 입니다."
        private const val ALREADY_PLACED = "이미 돌이 놓여있는 위치입니다."
    }
}
