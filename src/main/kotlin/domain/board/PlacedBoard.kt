package domain.board

import domain.stone.Color
import domain.stone.Column
import domain.stone.Position
import domain.stone.Row

class PlacedBoard(
    private val board: Map<Position, Color?> = POSITIONS.associateWith { null }.toMutableMap()
) {
    private val _placedBoard = board.filter { it.value != null }
    val size: Int = _placedBoard.size
    val whiteSize: Int = _placedBoard.filter { it.value == Color.WHITE }.size
    val blackSize: Int = _placedBoard.filter { it.value == Color.BLACK }.size
    private fun isPossiblePut(position: Position): Boolean {
        return board[position] == null
    }

    fun putStone(position: Position, color: Color): PlacedBoard {
        if (isPossiblePut(position).not()) return this
        val newBoard = board.toMutableMap()
        newBoard[position] = color
        return PlacedBoard(newBoard)
    }

    fun getBoards(): Map<Position, Color?> {
        return board.toMap()
    }

    companion object {
        private val POSITIONS: List<Position> = Column.values().flatMap { column ->
            Row.values().map { row ->
                Position(column, row)
            }
        }
    }
}
