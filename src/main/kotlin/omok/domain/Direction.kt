package omok.domain

enum class Direction(val y: Int, val x: Int) {
    TOP(1, 0),
    BOTTOM(-1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    TOP_RIGHT(1, 1),
    TOP_LEFT(1, -1),
    BOTTOM_LEFT(-1, -1),
    BOTTOM_RIGHT(-1, 1),
}
