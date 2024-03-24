package omok

import omok.model.Board
import omok.model.Coordinate
import omok.model.GameState

interface GameStateManager {
    fun play(
        onTurn: (GameState) -> Unit,
        onBoard: (Board) -> Unit,
        onCoordinate: () -> Coordinate,
    )
}
