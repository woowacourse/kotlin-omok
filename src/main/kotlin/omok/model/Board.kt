package omok.model

class Board {
    val board: List<List<StoneType?>>
        get() = _board.toList()
    private val _board: MutableList<MutableList<StoneType?>> =
        MutableList(15) {
            MutableList(15) { null }
        }

    fun putStone(stone: Stone) {
        _board[15 - (stone.point.row)][stone.point.column] = stone.type
    }
}
