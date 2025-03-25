package omok.model

import omok.model.position.Position
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
        val thisPoints: List<Point> = board.filterStones(color).extractPoints()
        val otherPoints: List<Point> = board.filterStones(color.reverse()).extractPoints()

        val rule: OmokRule =
            when (color) {
                Color.BLACK -> BlackRenjuRule(board.col.value, board.row.value)
                Color.WHITE -> WhiteRenjuRule(board.col.value, board.row.value)
            }

        val violation: Violation = rule.checkAnyFoulCondition(thisPoints, otherPoints, newPoint)
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
                Color.BLACK -> BlackRenjuRule(board.col.value, board.row.value)
                Color.WHITE -> WhiteRenjuRule(board.col.value, board.row.value)
            }

        val points: List<Point> = board.filterStones(color).extractPoints()
        val newPoint: Point = position.toPoint()
        val isOmok: Boolean = rule.checkSerialSameStonesBiDirection(points, newPoint, OMOK_CONDITION)

        if (!isOmok) return MoveResult.Success.Playing
        return when (color) {
            Color.BLACK -> MoveResult.Success.BlackWin
            Color.WHITE -> MoveResult.Success.WhiteWin
        }
    }

    private fun Position.toPoint(): Point {
        return Point(x.value, y.value)
    }

    private fun List<Stone>.extractPoints(): List<Point> {
        return map { stone -> stone.position.toPoint() }
    }

    companion object {
        private const val OMOK_CONDITION = 5
    }
}
