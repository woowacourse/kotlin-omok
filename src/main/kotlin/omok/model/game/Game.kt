package omok.model.game

import omok.model.Board
import omok.model.stone.Point
import omok.model.stone.Stone
import omok.model.stone.StoneColor

class Game(
    private val board: Board,
) {
    private var _lastStone = Stone(Point(1, 1), StoneColor.WHITE)
    val lastStone get() = _lastStone.copy()

    fun play(newStone: Stone) {
        board.place(newStone)
        _lastStone = Stone(newStone.point, _lastStone.color.reverse())
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
