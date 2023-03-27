package domain.stone

class Board(board: List<List<StoneType>> = List(16) { List(16) { StoneType.EMPTY } }) {

    private val _board = board.map { it.toMutableList() }.toMutableList()
    val board: List<List<StoneType>>
        get() = _board.map { it.toList() }.toList()

    val stones: Stones = Stones()

    fun putStone(stone: Stone) {
        _board[stone.position.x][stone.position.y] = when (_board[stone.position.x][stone.position.y]) {
            StoneType.EMPTY -> stone.type
            else -> throw IllegalStateException(STONE_EXIST_ERROR)
        }
        stones.add(stone)
    }

    companion object {
        private const val STONE_EXIST_ERROR = "바둑알이 이미 위치해 있습니다."
    }
}
