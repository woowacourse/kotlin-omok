package omok.model.rule

import omok.model.board.Board
import omok.model.board.Point
import omok.model.rule.count.OverlineRowRule
import omok.model.rule.lib.DoubleFourRule
import omok.model.rule.lib.DoubleThreeRule

class OmokJudge {
    fun validate(
        board: Board,
        previousPoint: Point,
    ): Boolean {
        val adaptedBoard = OmokAdapter.adaptOmokBoard(board)
        val adaptedPoint = OmokAdapter.adaptOmokPoint(previousPoint)

        if (DoubleThreeRule.validate(adaptedBoard, adaptedPoint)) return false
        if (DoubleFourRule.validate(adaptedBoard, adaptedPoint)) return false
        if (OverlineRowRule.calculate(board, previousPoint)) return false
        return true
    }
}
