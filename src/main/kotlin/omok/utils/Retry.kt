package omok.utils

fun <T> retry(
    action: () -> T,
    shouldRetry: (T) -> Boolean,
    onFailure: (Throwable) -> Unit,
): T {
    while (true) {
        runCatching {
            val result = action()
            if (!shouldRetry(result)) {
                return result
            }
        }.onFailure { e ->
            onFailure(e)
        }
    }
}
