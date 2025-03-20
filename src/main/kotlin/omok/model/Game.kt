package omok.model

import rule.wrapper.point.Point

class Game(
    val blackPlayer: BlackPlayer,
    val whitePlayer: WhitePlayer,
) {
    var lastStone = Stone(Point(1, 1), StoneColor.WHITE)

    fun play(point: Point) {
        val currentColor: StoneColor = reverse(lastStone.color)
        when (currentColor) {
            StoneColor.BLACK -> blackPlayer.place(point, whitePlayer.points)
            StoneColor.WHITE -> whitePlayer.place(point, blackPlayer.points)
        }
        lastStone = Stone(point, currentColor)
    }

    private fun reverse(stoneColor: StoneColor): StoneColor {
        return when (stoneColor) {
            StoneColor.BLACK -> StoneColor.WHITE
            StoneColor.WHITE -> StoneColor.BLACK
        }
    }
}
