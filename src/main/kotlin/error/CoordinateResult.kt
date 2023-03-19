package error

import domain.Coordinate

sealed class CoordinateResult : OmokError {
    data class Success(val coordinate: Coordinate) : CoordinateResult()
    object OutOfBoard : CoordinateResult()
}
