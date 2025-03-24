package omok.model.player.state

sealed class GameState {
    data object Playing : GameState()

    data object Win : GameState()

    data object ForbiddenMove : GameState()
}
