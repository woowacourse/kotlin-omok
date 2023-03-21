package omok.domain.board

import omok.domain.judgment.BlackReferee
import omok.domain.player.Stone

class Board(initialPositions: MutableMap<Position, Stone?> = POSITIONS.associateWith { null }.toMutableMap()) {
    private val referee = BlackReferee()
    private val cells = initialPositions.toMutableMap()
    val positions: Map<Position, Stone?>
        get() = cells.toMap()

    fun place(position: Position, stone: Stone) {
        if (stone.canPlace(referee, positions, position)) cells[position] = stone
    }

    companion object {
        val POSITIONS: List<Position> = Column.values().flatMap { column ->
            Row.values().map { row ->
                Position(column, row)
            }
        }
    }
}
