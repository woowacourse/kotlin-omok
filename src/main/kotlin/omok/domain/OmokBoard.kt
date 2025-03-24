package omok.domain

class OmokBoard(
    val width: Int = DEFAULT_SIZE,
    val height: Int = DEFAULT_SIZE,
    private val rule: Rule,
) {
    private val board: MutableList<MutableList<StoneState>> =
        MutableList(DEFAULT_SIZE) { MutableList(DEFAULT_SIZE) { StoneState.BLANK } }

    fun putStone(stone: Stone) {
        board[stone.position.y][stone.position.x] = stone.state
    }

    fun getStoneState(position: Position): StoneState = board[position.y][position.x]

    fun isStonePlaced(position: Position): Boolean {
        return getStoneState(position) != StoneState.BLANK
    }

    fun invalidPlace(stone: Stone): Boolean = rule.isViolate(this, stone)

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
        val x = position.x
        val y = position.y
        val state = board[y][x]
        var count = DEFAULT_COUNT

        while (checkRange(y + direction.colDelta * count, x + direction.rowDelta * count) &&
            board[y + direction.colDelta * count][x + direction.rowDelta * count] == state
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
    }
}
