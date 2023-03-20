package domain

import error.CoordinateError
import error.OmokResult

data class Coordinate private constructor(val vector: Vector) {
    val x: Int
        get() = vector.x
    val y: Int
        get() = vector.y

    operator fun plus(other: Vector): Coordinate? {
        return when (val result = from(vector.x + other.x, vector.y + other.y)) {
            is CoordinateError.OutOfBoard -> null
            is OmokResult.Success<*> -> result.value as Coordinate
        }
    }

    companion object {
        fun from(x: Int, y: Int): CoordinateError {
            if (x >= Board.BOARD_SIZE.x || y >= Board.BOARD_SIZE.y || x < Board.BOARD_START_Vector.x || y < Board.BOARD_START_Vector.y)
                return CoordinateError.OutOfBoard
            return OmokResult.Success(Coordinate(Vector(x, y)))
        }
    }
}
