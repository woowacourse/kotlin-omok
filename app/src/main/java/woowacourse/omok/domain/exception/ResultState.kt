package woowacourse.omok.domain.exception

sealed class ResultState<out T> {
    data class Success<T>(val data: T) : ResultState<T>()

    data class Error(val exceptions: Exceptions) : ResultState<Nothing>()
}
