package woowacourse.omok.model.state

sealed interface CoordinateState {
    data class Placed(val turn: Turn) : CoordinateState

    data object Empty : CoordinateState

    data object Forbidden : CoordinateState
}
