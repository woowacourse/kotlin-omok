package woowacourse.omok.domain.exception

sealed class Exceptions : Throwable() {
    object UnknownException : Exceptions()
}
