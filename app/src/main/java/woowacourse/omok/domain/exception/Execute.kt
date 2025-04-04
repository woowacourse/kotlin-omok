package woowacourse.omok.domain.exception

inline fun <T> execute(block: () -> T): ResultState<T> =
    runCatching {
        ResultState.Success(block())
    }.getOrElse {
        ResultState.Error(it as? Exceptions ?: Exceptions.UnknownException)
    }
