package omok.utils

fun <T> retry(
    action: () -> T,
    isFinish: (T) -> Boolean,
    onFailure: (Throwable) -> Unit,
): T {
    while (true) {
        runCatching {
            val result = action()
            if (isFinish(result)) return result
        }.onFailure { e ->
            onFailure(e)
        }
    }
}
