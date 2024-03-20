package omok.model

import omok.model.Rule.isWinCondition

class Board {
    val board: List<List<StoneType?>>
        get() = _board.toList()
    private val _board: MutableList<MutableList<StoneType?>> =
        MutableList(15) {
            MutableList(15) { null }
        }

    fun putStone(stone: Stone): Boolean {
        _board[stone.point.column][stone.point.row] = stone.type
        return isWinCondition(board, stone)
    }

    operator fun contains(point: Point): Boolean {
        return board[point.column][point.row] != null
    }
}
