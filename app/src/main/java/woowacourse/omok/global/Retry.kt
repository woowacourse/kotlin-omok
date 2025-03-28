package woowacourse.omok.global

import omok.event.GameEventListener

fun retryOnFailedToAddStone(
    event: GameEventListener,
    onFailure: (Throwable) -> Unit = { event.onInvalidInput(it.message) },
    block: () -> Unit,
) {
    runCatching {
        block()
    }.onFailure {
        onFailure(it)
    }
}
