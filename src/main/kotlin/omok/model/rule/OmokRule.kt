package omok.model.rule

import omok.model.board.Board
import omok.model.position.RelativeDirection
import omok.model.stone.StoneType

open class OmokRule {
    val directions = RelativeDirection.getRelativeDirections()

    fun search(
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
    ): Pair<Int, Int> {
        var toRight = x
        var toTop = y
        var stone = 0
        var blink = 0
        var blinkCount = 0
        while (true) {
            if (dx > 0 && Board.isBoardEdge(toRight)) break
            if (dx < 0 && Board.isBoardEdge(toRight)) break
            if (dy > 0 && Board.isBoardEdge(toTop)) break
            if (dy < 0 && Board.isBoardEdge(toTop)) break
            toRight += dx
            toTop += dy
            when (Board.getStoneType(toTop, toRight)) {
                CURRENT_STONEType -> {
                    stone++
                    blink = blinkCount
                }

                OTHER_STONEType -> break
                EMPTY_STONEType -> {
                    if (blink == 1) break
                    if (blinkCount++ == 1) break
                }

                else -> throw IllegalArgumentException()
            }
        }
        return Pair(stone, blink)
    }

    companion object {
        val EMPTY_STONEType = StoneType.NONE
        val CURRENT_STONEType = StoneType.BLACK_STONE
        val OTHER_STONEType: StoneType = StoneType.WHITE_STONE
    }
}
