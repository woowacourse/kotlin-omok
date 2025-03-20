package omok.model

import rule.wrapper.point.Point

class Game(
    val blackPlayer: BlackPlayer,
    val whitePlayer: WhitePlayer,
) {
    var lastStone = Stone(Point(1, 1), StoneColor.WHITE)

    fun play(point: Point): GameState {
        val currentColor: StoneColor = lastStone.color.reverse()
        val gameState: GameState =
            when (currentColor) {
                StoneColor.BLACK -> blackPlayer.place(point, whitePlayer.points)
                StoneColor.WHITE -> whitePlayer.place(point, blackPlayer.points)
            }
        lastStone = Stone(point, currentColor)
        return gameState
    }
}
