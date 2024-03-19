package omok.model

class Board {
    val board: List<List<StoneType>>
        get() = _board.toList()
    private val _board: MutableList<MutableList<StoneType>> =
        MutableList(15) {
            MutableList(15) {
                StoneType.EMPTY
            }
        }

    fun putStone(stone: Stone) {
        _board[stone.point.x][stone.point.y] = stone.type
    }
}
