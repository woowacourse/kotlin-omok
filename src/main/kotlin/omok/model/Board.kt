package omok.model

class Board {
    val board: List<List<StoneType>>
        get() = _board.toList()
    private val _board: MutableList<MutableList<StoneType>> =
        MutableList(BOARD_SIZE) {
            MutableList(BOARD_SIZE) { StoneType.EMPTY }
        }

    fun isForbidden(stone: Stone): Boolean {
        return RenjuRuleAdaptor.isForbidden(this, stone)
    }

    fun putStone(stone: Stone) {
        _board[stone.point.y][stone.point.x] = stone.type
    }

    operator fun contains(point: Point): Boolean {
        return board[point.y][point.x] != StoneType.EMPTY
    }

    companion object {
        private const val BOARD_SIZE = 15
    }
}
