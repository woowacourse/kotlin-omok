package omok.exception

inline fun <T> execute(block: () -> T): ResultState<T> =
    runCatching {
        ResultState.Success(block())
    }.getOrElse {
        ResultState.Error(it.message)
    }
