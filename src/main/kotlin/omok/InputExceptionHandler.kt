package omok

fun <T> retryWhileNotException(block: () -> T): T {
    return try {
        block()
    } catch (e: IllegalArgumentException) {
        println(e.message)
        retryWhileNotException(block)
    }
}
