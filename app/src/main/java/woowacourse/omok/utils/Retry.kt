package woowacourse.omok.utils

fun <T> retryOnException(
    action: () -> T,
    onFailure: (Throwable) -> Unit,
): T {
    while (true) {
        runCatching {
            return action()
        }.onFailure { e ->
            onFailure(e)
        }
    }
}
