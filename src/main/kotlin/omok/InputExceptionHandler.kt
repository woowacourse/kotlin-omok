package omok

tailrec fun <T> retryWhileNotException(block: () -> T): T {
    return runCatching {
        block()
    }.onFailure {
        println(it.message)
        return retryWhileNotException(block)
    }.getOrThrow()
}
