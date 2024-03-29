package woowacourse.omok

sealed interface CoordinateState {
    data object BlackStone : CoordinateState

    data object WhiteStone : CoordinateState

    data object Empty : CoordinateState

    data object Forbidden : CoordinateState
}
