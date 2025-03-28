package woowacourse.omok.model.player

sealed class GameState {
    data object Playing : GameState()

    data object Win : GameState()

    data object ForbiddenMove : GameState()

    protected var gameState: GameState = Playing

    fun win(): Boolean = gameState is Win

    fun playing(): Boolean = gameState is Playing

    fun forbidden(): Boolean = gameState is ForbiddenMove
}
