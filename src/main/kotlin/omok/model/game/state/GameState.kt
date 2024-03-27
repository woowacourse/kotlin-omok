package omok.model.game.state

import omok.model.Position
import omok.model.board.Board

sealed class GameState(val board: Board) {
    fun hasOmok(): Boolean = board.hasOmok()

    abstract fun placeStone(onPlace: () -> Position): GameState
}
