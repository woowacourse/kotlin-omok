package omok.domain

class OmokBoard(val width: Int = DEFAULT_SIZE, val height: Int = DEFAULT_SIZE) {
    val board: List<List<StoneState>>
        get() = _board.toList()
    private val _board: MutableList<MutableList<StoneState>> =
        MutableList(DEFAULT_SIZE) { MutableList(DEFAULT_SIZE) { StoneState.BLANK } }

    private val ruleAdaptor = OmokAdapter()

    fun putStone(stone: Stone) {
        _board[stone.position.y][stone.position.x] = stone.state
    }

    fun isStonePlaced(position: Position): Boolean {
        return _board[position.y][position.x] != StoneState.BLANK
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
        val x = position.x
        val y = position.y
        val state = _board[y][x]
        var count = DEFAULT_COUNT

        while (checkRange(y + direction.colDelta * count, x + direction.rowDelta * count) &&
            _board[y + direction.colDelta * count][x + direction.rowDelta * count] == state
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
