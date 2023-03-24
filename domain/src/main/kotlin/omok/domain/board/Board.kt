package omok.domain.board

import omok.domain.judgment.RenjuReferee
import omok.domain.player.Stone

class Board(initialPositions: MutableMap<Position, Stone?> = POSITIONS.associateWith { null }.toMutableMap()) {
    private val referee = RenjuReferee()
    private val cells = initialPositions.toMutableMap()
    val positions: Map<Position, Stone?>
        get() = cells.toMap()

    fun place(selectedPosition: Position, stone: Stone) {
        if (stone.canPlace(referee, positions, selectedPosition)) cells[selectedPosition] = stone
    }

    companion object {
        val POSITIONS: List<Position> = Column.values().flatMap { column ->
            Row.values().map { row ->
                Position(column, row)
            }
        }
    }
}
