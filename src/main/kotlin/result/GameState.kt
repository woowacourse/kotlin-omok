package result

import rule.type.Violation

sealed class GameState {
    data object Success : GameState()

    data class Fail(
        val violation: Violation,
    ) : GameState()

    fun isSuccess(): Boolean = this == Success
}
