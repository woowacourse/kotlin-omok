package error

sealed class OmokResult : CoordinateError, PlaceStoneError, StoneReadError {
    data class Success<T>(val value: T) : OmokResult()
}
