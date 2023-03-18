package domain.board

import domain.stone.Point
import domain.stone.Stone

data class Board(private val _placedStones: List<Stone> = listOf()) {

    val placedStones: List<Stone>
        get() = _placedStones.toList()

    val latestStone: Stone?
        get() = _placedStones.lastOrNull()

    fun isPlaced(point: Point): Boolean {

        return _placedStones.any { stone -> stone.point == point }
    }
}
