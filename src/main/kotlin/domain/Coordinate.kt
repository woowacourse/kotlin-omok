package domain

import error.CoordinateResult

data class Coordinate private constructor(val vector: Vector) {
    val x: Int
        get() = vector.x
    val y: Int
        get() = vector.y

    operator fun plus(other: Vector): Coordinate? {
        return when (val result = from(vector.x + other.x, vector.y + other.y)) {
            is CoordinateResult.OutOfBoard -> null
            is CoordinateResult.Success -> result.coordinate
        }
    }

    companion object {
        fun from(x: Int, y: Int): CoordinateResult {
            if (x >= Board.BOARD_SIZE.x || y >= Board.BOARD_SIZE.y || x < Board.BOARD_START_Vector.x || y < Board.BOARD_START_Vector.y)
                return CoordinateResult.OutOfBoard
            return CoordinateResult.Success(Coordinate(Vector(x, y)))
        }
    }
}
