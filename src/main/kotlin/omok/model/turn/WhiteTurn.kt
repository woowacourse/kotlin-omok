package omok.model.turn

import omok.model.Board
import omok.model.entity.Point
import omok.model.entity.Stone
import omok.model.entity.StoneColor
import omok.model.rule.FiveInRowRule

class WhiteTurn(val board: Board) : Turn {
    override fun placeStone(point: Point): Turn {
        val stone = Stone(point, StoneColor.WHITE)

        board.place(stone)
        if (board.isFull() || FiveInRowRule.check(board)) return Finished(StoneColor.WHITE)

        return BlackTurn(board)
    }

    override fun color(): StoneColor {
        return StoneColor.WHITE
    }
}
