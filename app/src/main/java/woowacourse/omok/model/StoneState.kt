package woowacourse.omok.model

sealed class StoneState {
    data object SuccessfulPlaced : StoneState()

    data class FailedPlaced(val message: String) : StoneState()
}
