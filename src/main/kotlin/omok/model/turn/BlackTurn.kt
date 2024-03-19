package omok.model.turn

import omok.model.Board
import omok.model.entity.Point
import omok.model.entity.StoneColor

class BlackTurn(val board: Board) : Turn {
    override fun placeStone(point: Point): Turn {
        board.place(point, StoneColor.BLACK)

        if (board.isFull() || board.startCheckOmok(StoneColor.BLACK)) return Finished(StoneColor.BLACK)

        return WhiteTurn(board)
    }

    override fun color(): StoneColor {
        return StoneColor.BLACK
    }
}
