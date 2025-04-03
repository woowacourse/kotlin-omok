package woowacourse.omok.domain

class OmokBoard(
    val size: Int = DEFAULT_SIZE,
    stones: List<Stone> = emptyList(),
    private val rule: Rule,
) {
    private val stonesByPosition: Map<Position, Stone> = stones.associateBy { it.position }

    private val board: MutableList<MutableList<StoneState>> =
        MutableList(size) { x ->
            MutableList(size) { y ->
                stonesByPosition[Position(y, x)]?.state ?: StoneState.BLANK
            }
        }

    fun putStone(stone: Stone): PutStoneResult {
        val position = Position(stone.position.x, stone.position.y)
        if (!checkRange(position.x, position.y)) return PutStoneResult.InvalidPosition
        if (isStonePlaced(stone)) return PutStoneResult.AlreadyPlaced
        if (isViolateRule(stone)) return PutStoneResult.Violation

        board[position.y][position.x] = stone.state
        if (checkOmok(stone.position)) return PutStoneResult.Finished
        return PutStoneResult.Success
    }

    fun getStoneState(position: Position): StoneState = board[position.y][position.x]

    private fun isStonePlaced(stone: Stone): Boolean = getStoneState(stone.position) != StoneState.BLANK

    private fun isViolateRule(stone: Stone): Boolean = rule.isViolate(this, stone)

    private fun checkOmok(position: Position): Boolean {
        val directions: List<Direction> =
            listOf(
                Direction(0, 1),
                Direction(1, 0),
                Direction(1, 1),
                Direction(1, -1),
            )

        return directions.any { dir ->
            val count = search(dir, position) + search(-dir, position) - 1
            count >= OMOK_STANDARD
        }
    }

    private fun search(
        direction: Direction,
        position: Position,
    ): Int {
        var currentX = position.x
        var currentY = position.y
        val state = getStoneState(position)
        var count = DEFAULT_COUNT

        while (checkRange(currentX, currentY) && isSameStone(currentX, currentY, state)) {
            count++
            currentX += direction.rowDelta
            currentY += direction.colDelta
        }
        return count
    }

    private fun isSameStone(
        currentX: Int,
        currentY: Int,
        state: StoneState,
    ): Boolean = getStoneState(Position(currentX, currentY)) == state

    private fun checkRange(
        coordinateX: Int,
        coordinateY: Int,
    ): Boolean = coordinateX in (MIN_BOUND..MAX_BOUND) && coordinateY in (MIN_BOUND..MAX_BOUND)

    companion object {
        const val MIN_BOUND = 0
        const val MAX_BOUND = 14
        const val DEFAULT_SIZE: Int = 15
        private const val DEFAULT_COUNT: Int = 0
        private const val OMOK_STANDARD: Int = 5
    }
}
