package omok.model

import rule.wrapper.point.Point

class Game(
    val blackPlayer: BlackPlayer,
    val whitePlayer: WhitePlayer,
) {
    private var _lastStone = Stone(Point(1, 1), Color.WHITE)
    val lastStone get() = _lastStone.copy()

    fun play(point: Point): GameState {
        val currentColor: Color = _lastStone.color.reverse()
        val gameState: GameState =
            when (currentColor) {
                Color.BLACK -> blackPlayer.place(point, whitePlayer.points)
                Color.WHITE -> whitePlayer.place(point, blackPlayer.points)
            }
        _lastStone = Stone(point, currentColor)
        return gameState
    }
}
