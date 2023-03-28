package domain

data class Coordinate private constructor(val point: Point) {
    val x: Int
        get() = point.x
    val y: Int
        get() = point.y

    operator fun plus(other: Point): Coordinate {
        return from(point.x + other.x, point.y + other.y)
    }

    companion object {
        fun from(x: Int, y: Int): Coordinate {
            require(
                x < Board.BOARD_END_POINT.x ||
                    y < Board.BOARD_END_POINT.y ||
                    x >= Board.BOARD_START_POINT.x ||
                    y >= Board.BOARD_START_POINT.y
            ) { ERROR_NOT_FOUNT_COORDINATE }
            return Coordinate(Point(x, y))
        }

        private const val ERROR_NOT_FOUNT_COORDINATE = "해당 좌표에 돌을 놓을 수 없습니다."
    }
}
