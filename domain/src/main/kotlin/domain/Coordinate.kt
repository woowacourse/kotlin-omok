package domain

data class Coordinate private constructor(val vector: Vector) {
    val x: Int
        get() = vector.x
    val y: Int
        get() = vector.y

    operator fun plus(other: Vector): Coordinate? {
        return from(vector.x + other.x, vector.y + other.y)
    }

    companion object {
        fun from(x: Int, y: Int): Coordinate? {
            if (x >= Board.BOARD_SIZE.x || y >= Board.BOARD_SIZE.y || x < Board.BOARD_START_Vector.x || y < Board.BOARD_START_Vector.y) return null
            return Coordinate(Vector(x, y))
        }
    }
}
