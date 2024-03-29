package woowacourse.omok.model

sealed class StoneState(
    open val message: String? = null,
) {
    data object SuccessfulPlaced : StoneState()

    data class FailedPlaced(
        override val message: String,
    ) : StoneState(message = message)
}
