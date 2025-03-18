package omok.board

import omok.stone.Stone

class Board(points: Set<Stone> = setOf()) {
    private val _points = points
    val points: Set<Stone>
        get() = _points.toSet()
}
