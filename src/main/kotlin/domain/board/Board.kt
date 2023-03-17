package domain.board

import domain.stone.Position
import domain.stone.Stone

class Board(private val _placedStones: List<Stone> = listOf()) {

    val placedStones: List<Stone>
        get() = _placedStones.toList()

    val latestStone: Stone
        get() = _placedStones.last()

    fun isPlaced(position: Position): Boolean {

        return _placedStones.any { stone -> stone.position == position }
    }

    operator fun plus(stone: Stone): Board {
        return Board(_placedStones + stone)
    }
}
