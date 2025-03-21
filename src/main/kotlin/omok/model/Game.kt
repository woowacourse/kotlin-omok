package omok.model

class Game(
    val blackPlayer: BlackPlayer,
    val whitePlayer: WhitePlayer,
) {
    private var _lastStone = Stone(Point(1, 1), StoneColor.WHITE)
    val lastStone get() = _lastStone.copy()

    fun play(point: Point): GameState {
        val currentColor: StoneColor = _lastStone.color.reverse()
        val gameState: GameState =
            when (currentColor) {
                StoneColor.BLACK -> blackPlayer.place(point, whitePlayer.points)
                StoneColor.WHITE -> whitePlayer.place(point, blackPlayer.points)
            }
        _lastStone = Stone(point, currentColor)
        return gameState
    }
}
