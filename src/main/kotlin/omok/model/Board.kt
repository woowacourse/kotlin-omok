package omok.model

import omok.model.entity.Point
import omok.model.entity.StoneColor

class Board {
    private val _board: MutableMap<Point, StoneColor> = mutableMapOf()
    val board: Map<Point, StoneColor>
        get() = _board.toMap()

    fun place(
        point: Point,
        color: StoneColor,
    ) {
        require(
            point.x in 1..15 &&
                point.y in 1..15,
        ) { "보드 밖에 돌을 두었습니다." }
        require(_board.contains(point).not()) { "그 포인트에 이미 돌이 존재합니다." }
        _board[point] = color
    }

    fun removePoint(point: Point) {
        _board.remove(point)
    }

    fun previousPoint(): Point? {
        return _board.keys.lastOrNull()
    }

    fun contains(point: Point): Boolean {
        return _board.contains(point)
    }

    fun isFull(): Boolean = _board.count() == 15 * 15
}
