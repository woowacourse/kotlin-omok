package error

sealed class OmokResult : CoordinateError, PlaceStoneError, StoneReadError {
    data class Success<T>(val value: T, override val message: String = "기능을 성공적으로 수행했습니다.") : OmokResult()
}
