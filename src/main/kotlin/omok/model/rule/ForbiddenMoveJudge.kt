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
        val convertedBoard = OmokConverter.converteOmokBoard(board)
        val convertedPoint = OmokConverter.converteOmokPoint(previousPoint)

        if (DoubleThreeMoveRule.validate(convertedBoard, convertedPoint)) return false
        if (DoubleFourMoveRule.validate(convertedBoard, convertedPoint)) return false
        if (OverlineRule.calculate(board, previousPoint)) return false
        return true
    }
}
