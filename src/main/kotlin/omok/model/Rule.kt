package omok.model

import rule.BlackRenjuRule
import rule.OmokRule
import rule.WhiteRenjuRule
import rule.type.Violation
import rule.wrapper.point.Point

class Rule {
    fun checkViolation(
        board: Board,
        position: Position,
        color: Color,
    ): MoveResult {
        val newPoint: Point = position.toPoint()
        val blackPoints: List<Point> =
            board.filterStones(Color.BLACK).map { stone -> stone.position.toPoint() }
        val whitePoints: List<Point> =
            board.filterStones(Color.WHITE).map { stone -> stone.position.toPoint() }
        val violation: Violation =
            when (color) {
                Color.BLACK ->
                    BlackRenjuRule(board.col, board.row).checkAnyFoulCondition(blackPoints, whitePoints, newPoint)
                Color.WHITE ->
                    WhiteRenjuRule(board.col, board.row).checkAnyFoulCondition(whitePoints, blackPoints, newPoint)
            }

        return when (violation) {
            Violation.DOUBLE_THREE -> MoveResult.Failure.DoubleThreeViolation
            Violation.DOUBLE_FOUR -> MoveResult.Failure.DoubleFourViolation
            Violation.OVERLINE -> MoveResult.Failure.OverlineViolation
            Violation.NONE -> MoveResult.Success.Playing
        }
    }

    fun checkOmok(
        board: Board,
        position: Position,
        color: Color,
    ): MoveResult {
        val rule: OmokRule =
            when (color) {
                Color.BLACK -> BlackRenjuRule(board.col, board.row)
                Color.WHITE -> WhiteRenjuRule(board.col, board.row)
            }
        val points: List<Point> = board.filterStones(color).map { stone -> stone.position.toPoint() }
        val newPoint: Point = position.toPoint()
        val isOmok: Boolean = rule.checkSerialSameStonesBiDirection(points, newPoint, OMOK_CONDITION)
        if (!isOmok) return MoveResult.Success.Playing
        return when (color) {
            Color.BLACK -> MoveResult.Success.BlackWin
            Color.WHITE -> MoveResult.Success.WhiteWin
        }
    }

    private fun Position.toPoint(): Point {
        return Point(x, y)
    }

    companion object {
        private const val OMOK_CONDITION = 5
    }
}
