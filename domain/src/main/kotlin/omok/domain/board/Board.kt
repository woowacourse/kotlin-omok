package omok.domain.board

import omok.domain.judgment.NormalReferee
import omok.domain.judgment.PlacementReferee
import omok.domain.judgment.RenjuReferee
import omok.domain.player.Stone

class Board(
    initialPositions: MutableMap<Position, Stone?> = POSITIONS.associateWith { null }.toMutableMap()
) {
    private val referee: Map<Stone, PlacementReferee> = mapOf(Stone.BLACK to RenjuReferee(Stone.BLACK))
    private val cells = initialPositions.toMutableMap()
    val positions: Map<Position, Stone?>
        get() = cells.toMap()

    fun place(selectedPosition: Position, stone: Stone) {
        val referee = referee[stone] ?: NormalReferee(stone)

        require(!referee.isForbiddenPlacement(positions, selectedPosition)) {
            "[ERROR] ${selectedPosition.column.name}${selectedPosition.row.axis + 1}은/는 금수입니다."
        }
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
