package omok

class Board {
    val map: MutableMap<Point, StoneColor> = mutableMapOf()
    fun place(point: Point, color: StoneColor) {
        require(
            point.x in 1..15 &&
                point.y in 1..15,
        ) { "보드 밖에 돌을 두었습니다." }
        map[point] = color
    }

    fun contains(point: Point): Boolean {
        return map.contains(point)
    }
}
