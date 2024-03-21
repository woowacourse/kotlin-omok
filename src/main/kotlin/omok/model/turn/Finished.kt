package omok.model.turn

import omok.model.Board
import omok.model.entity.Point
import omok.model.entity.StoneColor

class Finished(board: Board) : Turn(board) {
    override fun placeStone(point: Point): Turn {
        throw IllegalStateException()
    }

    override fun color(): StoneColor {
        val stone = board.previousStone() ?: return StoneColor.BLACK
        return stone.stoneColor
    }
}
