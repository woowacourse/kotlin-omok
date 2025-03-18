package omok.board

import omok.stone.Stone

class Board(points: Set<Stone> = setOf()) {
    private val _points = points.toMutableSet()
    val points: Set<Stone>
        get() = _points.toSet()

    fun addStone(stone: Stone) {
        _points.add(stone)
    }
}
