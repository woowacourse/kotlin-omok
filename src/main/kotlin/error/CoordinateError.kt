package error

sealed interface CoordinateError : OmokError {
    object OutOfBoard : CoordinateError
}
