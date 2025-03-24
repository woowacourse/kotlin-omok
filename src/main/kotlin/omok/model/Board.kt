package omok.model

class Board {
    val row: Int = DEFAULT_BOARD_SIZE
    val col: Int = DEFAULT_BOARD_SIZE
    private val _stones: MutableSet<Stone> = mutableSetOf()
    val stones: Set<Stone> get() = _stones.toSet()

    fun add(newStone: Stone): MoveResult {
        if (_stones.map { stone -> stone.position }.contains(newStone.position)) {
            return MoveResult.Failure.PositionAlreadyOccupied
        }
        _stones.add(newStone)
        return MoveResult.Success.Playing
    }

    fun filterStones(color: Color): List<Stone> = _stones.filter { stone -> stone.color == color }

    companion object {
        private const val DEFAULT_BOARD_SIZE = Position.MAX_POSITION - Position.MIN_POSITION + 1
    }
}
