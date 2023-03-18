package omok.domain.board

import omok.domain.judgment.BlackReferee
import omok.domain.player.Stone

class Board(_positions: MutableMap<Position, Stone?> = POSITIONS.associateWith { null }.toMutableMap()) {
    private val referee = BlackReferee()
    private val cells = _positions.toMutableMap()
    val positions: Map<Position, Stone?>
        get() = cells.toMap()

    fun place(position: Position, stone: Stone) {
        require(isEmpty(position)) {
            "[ERROR] ${position.column.name}${position.row.axis + 1}은/는 비어있지 않습니다."
        }
        if (stone.canPlace(referee, positions, position)) cells[position] = stone
    }

    private fun isEmpty(position: Position) = cells[position] == null

    companion object {
        val POSITIONS: List<Position> = Column.values().flatMap { column ->
            Row.values().map { row ->
                Position(column, row)
            }
        }
    }
}
