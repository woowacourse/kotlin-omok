package omok.model.turn

import omok.model.Board
import omok.model.entity.Point
import omok.model.entity.StoneColor
import omok.model.rule.OmokRule

class WhiteTurn(val board: Board) : Turn {
    override fun placeStone(point: Point): Turn {
        board.place(point, StoneColor.WHITE)

        if (board.isFull() || OmokRule().check(board, StoneColor.WHITE, point)) return Finished(StoneColor.WHITE)

        return BlackTurn(board)
    }

    override fun color(): StoneColor {
        return StoneColor.WHITE
    }
}
