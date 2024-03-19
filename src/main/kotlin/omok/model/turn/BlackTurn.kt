package omok.model.turn

import omok.model.Board
import omok.model.entity.Point
import omok.model.entity.StoneColor

class BlackTurn(val board: Board) : Turn {
    override fun placeStone(point: Point): Turn {
        board.place(point, StoneColor.BLACK)
        return WhiteTurn(board)
    }
}
