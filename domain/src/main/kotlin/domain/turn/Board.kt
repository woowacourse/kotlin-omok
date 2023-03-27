package domain.turn

import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

class Board(
    board: Map<Position, Color?>
) {
    private val _map: Map<Position, Color?> = board.toMap()
    val map: Map<Position, Color?>
        get() = _map.toMap()

    fun isAlreadyPut(position: Position): Boolean = (map[position] != null)

    fun putStone(stone: Stone): Board {
        return Board(map.plus(stone.position to stone.color))
    }
}
