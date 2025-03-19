package omok.util

fun <T> retryInput(inputFunction: () -> T): T {
    return runCatching { inputFunction() }
        .getOrElse { e ->
            println(e.message)
            retryInput(inputFunction)
        }
}
