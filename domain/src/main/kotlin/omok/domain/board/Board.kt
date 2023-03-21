package omok.domain.board

import omok.domain.judgment.PlacementReferee
import omok.domain.player.Stone

class Board(positions: Map<Position, Stone?> = POSITIONS.associateWith { null }) {
    private val _positions: MutableMap<Position, Stone?> = positions.toMutableMap()
    val positions: Map<Position, Stone?>
        get() = _positions.toMap()

    fun placeStone(position: Position, stone: Stone, referee: PlacementReferee) {
        require(isEmpty(position)) {
            "해당 위치는 이미 돌이 존재 합니다."
        }
        if (stone.canPlace(referee, positions, position)) _positions[position] = stone
    }

    private fun isEmpty(position: Position) = _positions[position] == null

    companion object {
        val POSITIONS: List<Position> = Column.values().flatMap { column ->
            Row.values().map { row ->
                Position(column, row)
            }
        }
    }
}
