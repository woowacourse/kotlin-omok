package omok.model

interface GameStateManager {
    fun play(
        onTurn: (GameState) -> Unit,
        onBoard: (Board) -> Unit,
        onCoordinate: () -> Coordinate,
    )
}
