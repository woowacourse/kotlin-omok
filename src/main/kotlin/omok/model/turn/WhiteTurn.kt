package omok.model.turn

import omok.model.Board
import omok.model.entity.Point
import omok.model.entity.StoneColor

class WhiteTurn(val board: Board) : Turn {
    override fun placeStone(point: Point): Turn {
        board.place(point, StoneColor.WHITE)
        return BlackTurn(board)
    }

    override fun color(): StoneColor {
        return StoneColor.WHITE
    }
}
