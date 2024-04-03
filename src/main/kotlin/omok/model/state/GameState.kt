package omok.model.state

import omok.model.GameResult

sealed class GameState(
    open val message: String? = null,
) {
    data object OnProgress : GameState()

    data class GameOver(
        val gameResult: GameResult,
    ) : GameState()

    data class Error(
        override val message: String,
    ) : GameState(message = message)
}
