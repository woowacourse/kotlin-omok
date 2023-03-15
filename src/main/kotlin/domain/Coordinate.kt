package domain

data class Coordinate private constructor(override val x: Int, override val y: Int) : Point(x, y) {
    private constructor(point: Point) : this(point.x, point.y)

    override fun plus(other: Point): Coordinate {
        return Coordinate(super.plus(other))
    }

    companion object {
        fun from(x: Int, y: Int): Coordinate? {
            if (x >= Board.BOARD_SIZE.x || y >= Board.BOARD_SIZE.y || x < Board.BOARD_START_POINT.x || y < Board.BOARD_START_POINT.y) return null
            return Coordinate(x, y)
        }
    }
}
