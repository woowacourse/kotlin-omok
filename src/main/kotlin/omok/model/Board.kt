package omok.model

class Board {
    val board: List<List<StoneType?>>
        get() = _board.toList()
    private val _board: MutableList<MutableList<StoneType?>> =
        MutableList(15) {
            MutableList(15) { null }
        }

    fun putStone(stone: Stone) {
        _board[stone.point.column][stone.point.row] = stone.type
    }

    operator fun contains(point: Point): Boolean {
        return board[point.column][point.row] != null
    }
}
