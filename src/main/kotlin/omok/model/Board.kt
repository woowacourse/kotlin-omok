package omok.model

class Board(
    val row: Int = DEFAULT_BOARD_SIZE,
    val col: Int = DEFAULT_BOARD_SIZE,
) {
    private val _stones: MutableSet<Stone> = mutableSetOf()
    val stones: Set<Stone> get() = _stones.toSet()

    fun add(newStone: Stone) {
        require(!_stones.map { stone -> stone.position }.contains(newStone.position)) { ERROR_MESSAGE_POSITION_ALREADY_OCCUPIED }
        _stones.add(newStone)
    }

    fun filterStones(color: Color): List<Stone> = _stones.filter { stone -> stone.color == color }

    companion object {
        private const val DEFAULT_BOARD_SIZE = Position.MAX_POSITION - Position.MIN_POSITION + 1
        private const val ERROR_MESSAGE_POSITION_ALREADY_OCCUPIED = "이미 돌이 있는 자리입니다."
    }
}
