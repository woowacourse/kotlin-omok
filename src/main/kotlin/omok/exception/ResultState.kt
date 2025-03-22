package omok.exception

sealed class ResultState<out T> {
    data class Success<T>(val data: T) : ResultState<T>()

    data class Error(val message: String?) : ResultState<Nothing>()
}

fun <T> ResultState<T>.recover(onError: (String?) -> T): T {
    return when (this) {
        is ResultState.Success -> data
        is ResultState.Error -> onError(message)
    }
}
