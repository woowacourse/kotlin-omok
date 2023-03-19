package domain.stone

class Board(board: List<List<StoneType>> = List(16) { List(16) { StoneType.EMPTY } }) {

    private val _board = board.map { it.toMutableList() }.toMutableList()
    val board: List<List<StoneType>>
        get() = _board.map { it.toList() }.toList()

    val stones: Stones = Stones()

    fun putStone(stone: Stone) {
        _board[stone.position.y][stone.position.x] = stone.type
        stones.add(stone)
    }

    companion object {
        const val BOARD_SIZE = 15
    }
}
