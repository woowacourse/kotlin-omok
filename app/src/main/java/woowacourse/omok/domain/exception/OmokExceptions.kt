package woowacourse.omok.domain.exception

sealed class OmokExceptions : Exceptions() {
    object OccupiedExceptions : OmokExceptions()
}
