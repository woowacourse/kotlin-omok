package woowacourse.omok.adapter

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
import woowacourse.omok.model.rule.Rule

class RuleAdapter : Rule {
    override fun checkForbiddenMove(
        board: Board,
        newStone: Stone,
    ): MoveResult {
        if (isUnavailablePosition(board, newStone)) return MoveResult.Failure.PositionAlreadyOccupied
        return when (checkViolation(board, newStone)) {
            Violation.DOUBLE_THREE -> MoveResult.Failure.DoubleThreeViolation
            Violation.DOUBLE_FOUR -> MoveResult.Failure.DoubleFourViolation
            Violation.OVERLINE -> MoveResult.Failure.OverlineViolation
            Violation.NONE -> MoveResult.Success.Playing
        }
    }

    private fun isUnavailablePosition(
        board: Board,
        newStone: Stone,
    ): Boolean {
        return board.stones.map(Stone::position).contains(newStone.position)
    }

    private fun checkViolation(
        board: Board,
        newStone: Stone,
    ): Violation {
        val newPoint: Point = newStone.position.toPoint()
        val thisPoints: List<Point> = board.filterStones(newStone.color).extractPoints()
        val otherPoints: List<Point> = board.filterStones(newStone.color.reverse()).extractPoints()

        val rule: OmokRule =
            when (newStone.color) {
                Color.BLACK -> BlackRenjuRule(board.col.value, board.row.value)
                Color.WHITE -> WhiteRenjuRule(board.col.value, board.row.value)
            }

        return rule.checkAnyFoulCondition(thisPoints, otherPoints, newPoint)
    }

    override fun checkWinCondition(
        board: Board,
        newStone: Stone,
    ): MoveResult {
        return if (isOmok(board, newStone)) {
            MoveResult.Success.Finished(newStone.color)
        } else {
            MoveResult.Success.Playing
        }
    }

    private fun isOmok(
        board: Board,
        newStone: Stone,
    ): Boolean {
        val rule: OmokRule =
            when (newStone.color) {
                Color.BLACK -> BlackRenjuRule(board.col.value, board.row.value)
                Color.WHITE -> WhiteRenjuRule(board.col.value, board.row.value)
            }
        val points: List<Point> = board.filterStones(newStone.color).extractPoints()
        val newPoint: Point = newStone.position.toPoint()
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
