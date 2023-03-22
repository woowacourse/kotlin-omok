package domain.stone

class Board {

    val stones: Stones = Stones()
    val board: List<List<StoneType>>
        get() = _board.toList()
    private val _board: MutableList<MutableList<StoneType>> =
        MutableList(16) { MutableList(16) { StoneType.EMPTY } }

    fun putStone(stone: Stone) {
        _board[stone.position.y][stone.position.x] = stone.type
        stones.add(stone)
    }

    companion object {
        const val BOARD_SIZE = 15
    }
}
