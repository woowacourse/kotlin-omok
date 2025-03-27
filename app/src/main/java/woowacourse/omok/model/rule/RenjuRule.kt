package woowacourse.omok.model.rule

import rule.BlackRenjuRule
import rule.OmokRule
import rule.WhiteRenjuRule
import rule.type.Violation
import rule.wrapper.point.Point
import woowacourse.omok.model.Board
import woowacourse.omok.model.Color
import woowacourse.omok.model.MoveResult
import woowacourse.omok.model.Stone
import woowacourse.omok.model.position.Position

class RenjuRule : Rule {
    override fun checkForbiddenMove(
        board: Board,
        position: Position,
        color: Color,
    ): MoveResult {
        if (isUnavailablePosition(board, position, color)) return MoveResult.Failure.PositionAlreadyOccupied
        return when (checkViolation(board, position, color)) {
            Violation.DOUBLE_THREE -> MoveResult.Failure.DoubleThreeViolation
            Violation.DOUBLE_FOUR -> MoveResult.Failure.DoubleFourViolation
            Violation.OVERLINE -> MoveResult.Failure.OverlineViolation
            Violation.NONE -> MoveResult.Success.Playing
        }
    }

    private fun isUnavailablePosition(
        board: Board,
        position: Position,
        color: Color,
    ): Boolean {
        return board.stones.map(Stone::position).contains(position)
    }

    private fun checkViolation(
        board: Board,
        position: Position,
        color: Color,
    ): Violation {
        val newPoint: Point = position.toPoint()
        val thisPoints: List<Point> = board.filterStones(color).extractPoints()
        val otherPoints: List<Point> = board.filterStones(color.reverse()).extractPoints()

        val rule: OmokRule =
            when (color) {
                Color.BLACK -> BlackRenjuRule(board.col.value, board.row.value)
                Color.WHITE -> WhiteRenjuRule(board.col.value, board.row.value)
            }

        return rule.checkAnyFoulCondition(thisPoints, otherPoints, newPoint)
    }

    override fun checkWinCondition(
        board: Board,
        position: Position,
        color: Color,
    ): MoveResult {
        return if (isOmok(board, position, color)) MoveResult.Success.Finished(color) else MoveResult.Success.Playing
    }

    private fun isOmok(
        board: Board,
        position: Position,
        color: Color,
    ): Boolean {
        val rule: OmokRule =
            when (color) {
                Color.BLACK -> BlackRenjuRule(board.col.value, board.row.value)
                Color.WHITE -> WhiteRenjuRule(board.col.value, board.row.value)
            }
        val points: List<Point> = board.filterStones(color).extractPoints()
        val newPoint: Point = position.toPoint()
        return rule.checkSerialSameStonesBiDirection(points, newPoint, OMOK_CONDITION)
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
