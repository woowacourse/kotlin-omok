package omok.domain.board

import omok.domain.judgment.NormalReferee
import omok.domain.judgment.PlacementReferee
import omok.domain.judgment.RenjuReferee
import omok.domain.player.Black
import omok.domain.player.Stone

class Board(
    initialPositions: MutableMap<Position, Stone?> = POSITIONS.associateWith { null }.toMutableMap()
) {
    private val referee: Map<Stone, PlacementReferee> = mapOf(Black to RenjuReferee(Black))
    private val cells = initialPositions.toMutableMap()
    val positions: Map<Position, Stone?>
        get() = cells.toMap()

    fun place(selectedPosition: Position, stone: Stone) {
        val referee = referee[stone] ?: NormalReferee(stone)

        if (stone.canPlace(referee, positions, selectedPosition))
            cells[selectedPosition] = stone
    }

    companion object {
        val POSITIONS: List<Position> = Column.values().flatMap { column ->
            Row.values().map { row ->
                Position(column, row)
            }
        }
    }
}
