package omok.turn

import omok.Board
import omok.Point
import omok.StoneColor

class BlackTurn(val board: Board) : Turn {
    override fun placeStone(point: Point): Turn {
        board.place(point, StoneColor.BLACK)
        return WhiteTurn(board)
    }
}
