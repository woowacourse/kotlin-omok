package woowacourse.omok.util

fun <T> retryInput(
    inputFunction: () -> T,
    printErrorMessage: (String?) -> Unit,
): T {
    return runCatching { inputFunction() }
        .getOrElse { e ->
            printErrorMessage(e.message)
            retryInput(inputFunction, printErrorMessage)
        }
}
