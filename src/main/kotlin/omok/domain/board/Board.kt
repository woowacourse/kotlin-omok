package omok.domain.board

import omok.domain.player.Stone

class Board(private val positions: MutableMap<Position, Stone?> = POSITIONS.associateWith { null }.toMutableMap()) {

    fun isEmpty(position: Position) = positions[position] == null

    fun place(position: Position, stone: Stone) {
        check(isEmpty(position)) { "[ERROR] 해당 위치는 비어있지 않습니다." }
        positions[position] = stone
    }

    companion object {
        val POSITIONS: List<Position> = Column.values().flatMap { column ->
            Row.values().map { row ->
                Position(column, row)
            }
        }
    }
}
