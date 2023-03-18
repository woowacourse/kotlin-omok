package domain

data class Coordinate private constructor(val vector: Vector) {
    val x: Int
        get() = vector.x
    val y: Int
        get() = vector.y

    operator fun plus(other: Vector): Coordinate? {
        return from(vector.x + other.x, vector.y + other.y).getOrNull()
    }

    companion object {
        fun from(x: Int, y: Int): Result<Coordinate> {
            if (x >= Board.BOARD_SIZE.x || y >= Board.BOARD_SIZE.y || x < Board.BOARD_START_Vector.x || y < Board.BOARD_START_Vector.y)
                return Result.failure(IllegalArgumentException(MESSAGE_CORRUPTED_COORDINATE.format('A' + x, y + 1)))
            return Result.success(Coordinate(Vector(x, y)))
        }

        private const val MESSAGE_CORRUPTED_COORDINATE = "보드의 범위에 벗어나는 좌표입니다. 입력 좌표 (%c, %d)"
    }
}
