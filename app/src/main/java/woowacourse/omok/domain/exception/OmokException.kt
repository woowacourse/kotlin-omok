package woowacourse.omok.domain.exception

sealed class OmokException : Exceptions() {
    object OccupiedExceptions : OmokException()
}
