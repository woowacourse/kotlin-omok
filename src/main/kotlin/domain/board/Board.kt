package domain.board

import domain.player.Player

class Board(private val positions: Map<Position, Player?> = POSITIONS.associateWith { null }) {

    fun isEmpty(position: Position) = positions[position] == null

    companion object {
        val POSITIONS: List<Position> = Column.values().flatMap { column ->
            Row.values().map { row ->
                Position(column, row)
            }
        }
    }
}
