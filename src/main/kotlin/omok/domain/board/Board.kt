package omok.domain.board

import omok.domain.judgment.BlackReferee
import omok.domain.player.Black
import omok.domain.player.Stone

class Board(private val _positions: MutableMap<Position, Stone?> = POSITIONS.associateWith { null }.toMutableMap()) {
    private val referee = BlackReferee()
    val positions: Map<Position, Stone?>
        get() = _positions.toMap()

    fun place(position: Position, stone: Stone) {
        require(isEmpty(position)) { "[ERROR] 해당 위치는 비어있지 않습니다." }
        if (stone == Black) require(!referee.isForbiddenPlacement(positions, position)) { "[ERROR] 해당 위치는 금수입니다." }
        _positions[position] = stone
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
