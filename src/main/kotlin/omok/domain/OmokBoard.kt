package omok.domain

class OmokBoard(val width: Int = DEFAULT_SIZE, val height: Int = DEFAULT_SIZE) {
    val positions: List<Position> = (1..15).flatMap { x -> (1..15).map { y -> Position(x, y) } }
    private val ruleAdaptor = OmokAdapter()

    fun findPoint(
        x: Int,
        y: Int,
    ): Position? {
        return positions.find { it.x == x && it.y == y }
    }

    fun putStone(
        position: Position,
        state: StoneState,
    ) {
        findPoint(position.x, position.y)?.changeState(state)
    }

    fun invalidPlace(stone: Stone): Boolean = ruleAdaptor.isViolate(this, stone)

    fun checkOmok(position: Position): Boolean {
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
        val coordinateX = position.x
        val coordinateY = position.y
        val state = positions.find { it.x == coordinateX && it.y == coordinateY }?.stoneState
        var count = DEFAULT_COUNT

        while (checkRange(coordinateX + direction.rowDelta * count, coordinateY + direction.colDelta * count) &&
            positions.find { it.x == coordinateX + direction.rowDelta * count && it.y == coordinateY + direction.colDelta * count }?.stoneState == state
        ) {
            count++
        }

        return count
    }

    private fun checkRange(
        coordinateX: Int,
        coordinateY: Int,
    ): Boolean {
        return coordinateX in (MIN_BOUND..MAX_BOUND) && coordinateY in (MIN_BOUND..MAX_BOUND)
    }

    companion object {
        const val MIN_BOUND = 0
        const val MAX_BOUND = 14
        const val DEFAULT_SIZE: Int = 15
        private const val DEFAULT_COUNT: Int = 0
        private const val OMOK_STANDARD: Int = 5
        private const val ERROR_STONE_ALREADY_PUT = "이미 돌이 있습니다."
    }
}
