package omok.model.turn

import omok.model.Board
import omok.model.entity.Point
import omok.model.entity.StoneColor
import omok.model.rule.FourFourRule
import omok.model.rule.OmokRule
import omok.model.rule.SamSamRule

class BlackTurn(val board: Board) : Turn {
    override fun placeStone(point: Point): Turn {
        board.place(point, StoneColor.BLACK)

        if (SamSamRule.check(board, StoneColor.BLACK) || FourFourRule.check(board, StoneColor.BLACK)) {
            board.removePoint(point)
            return BlackTurn(board)
        }

        if (board.isFull() || OmokRule.check(board, StoneColor.BLACK)) return Finished(StoneColor.BLACK)

        return WhiteTurn(board)
    }

    override fun color(): StoneColor {
        return StoneColor.BLACK
    }
}
