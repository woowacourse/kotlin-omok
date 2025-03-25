package omok.model.game

import omok.model.Board
import omok.model.stone.Stone
import omok.model.stone.StoneColor

class Game(
    private val board: Board,
) {
    private var _lastStone: Stone? = null
    val lastStone get() = _lastStone?.copy()

    fun play(newStone: Stone) {
        board.place(newStone)
        _lastStone = newStone
    }

    fun gameState(newStone: Stone): GameState {
        if (!board.hasOmok(newStone)) {
            return GameState.PLAYING
        }
        return when (newStone.color) {
            StoneColor.BLACK -> GameState.BLACK_OMOK
            StoneColor.WHITE -> GameState.WHITE_OMOK
        }
    }
}
