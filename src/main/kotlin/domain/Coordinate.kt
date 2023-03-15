package domain

data class Coordinate private constructor(val point: Point) {
    val x: Int
        get() = point.x
    val y: Int
        get() = point.y

    operator fun plus(other: Point): Coordinate? {
        return from(point.x + other.x, point.y + other.y)
    }

    companion object {
        fun from(x: Int, y: Int): Coordinate? {
            if (x >= Board.BOARD_SIZE.x || y >= Board.BOARD_SIZE.y || x < Board.BOARD_START_POINT.x || y < Board.BOARD_START_POINT.y) return null
            return Coordinate(Point(x, y))
        }
    }
}
