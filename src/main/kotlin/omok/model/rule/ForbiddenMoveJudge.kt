package omok.model.rule

import omok.model.board.Board
import omok.model.board.Point
import omok.model.rule.count.OverlineRule
import omok.model.rule.lib.DoubleFourMoveRule
import omok.model.rule.lib.DoubleThreeMoveRule

object ForbiddenMoveJudge {
    fun validate(
        board: Board,
        previousPoint: Point,
    ): Boolean {
        val adaptedBoard = OmokAdapter.adaptOmokBoard(board)
        val adaptedPoint = OmokAdapter.adaptOmokPoint(previousPoint)

        if (DoubleThreeMoveRule.validate(adaptedBoard, adaptedPoint)) return false
        if (DoubleFourMoveRule.validate(adaptedBoard, adaptedPoint)) return false
        if (OverlineRule.calculate(board, previousPoint)) return false
        return true
    }
}
