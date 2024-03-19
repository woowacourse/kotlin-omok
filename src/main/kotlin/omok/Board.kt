package omok

class Board {
    val map: MutableMap<Point, StoneColor> = mutableMapOf()
    fun place(point: Point, color: StoneColor) {
        require(
            point.x in 1..15 &&
                point.y in 1..15,
        ) { "보드 밖에 돌을 두었습니다." }
        require(map.contains(point).not()) { "그 포인트에 이미 돌이 존재합니다." }
        map[point] = color
    }

    fun previousPoint(): Point {
        return map.keys.lastOrNull() ?: throw NoSuchElementException("첫 돌이기에 이전 이력이 없습니다.")
    }

    fun contains(point: Point): Boolean {
        return map.contains(point)
    }
}
