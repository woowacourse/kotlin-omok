package domain.board

import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

class PlacedBoard(
    private val board: Map<Position, Color?> = POSITIONS.associateWith { null }.toMutableMap()
) {
    private val _placedBoard = board.filter { it.value != null }
    val size: Int
        get() = _placedBoard.size
    val whiteSize: Int
        get() = _placedBoard.filter { it.value == Color.WHITE }.size
    val blackSize: Int
        get() = _placedBoard.filter { it.value == Color.BLACK }.size

    private fun isPossiblePut(position: Position): Boolean {
        return board[position] == null
    }

    fun putStone(stone: Stone): PlacedBoard {
        if (isPossiblePut(stone.position).not()) return this
        val newBoard = board.toMutableMap()
        newBoard[stone.position] = stone.color
        return PlacedBoard(newBoard)
    }

    fun getBoards(): Map<Position, Color?> {
        return board.toMap()
    }

    companion object {
        private val POSITIONS: List<Position> = Position.all()
    }
}
