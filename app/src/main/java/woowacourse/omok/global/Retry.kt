package omok.global

import omok.view.InputView
import omok.view.OutputView

fun <T> retryWhenException(
    onError: (String?) -> Unit = OutputView::printErrorMessage,
    action: () -> T,
): T {
    while (true) {
        runCatching {
            return action()
        }.onFailure {
            onError(it.message)
        }
    }
}

fun <T> retryWhenNull(
    onNull: () -> Unit = InputView::printOnNull,
    action: () -> T?,
): T {
    while (true) {
        val result = action()
        if (result != null) {
            return result
        }
        onNull()
    }
}
