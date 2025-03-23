package omok.model.rule

import omok.model.board.Board
import omok.model.board.BoardSize
import omok.model.stone.StoneState
import omok.model.stone.position.Position
import rule.BlackRenjuRule
import rule.OmokRule
import rule.type.Violation.DOUBLE_FOUR
import rule.type.Violation.DOUBLE_THREE
import rule.type.Violation.NONE
import rule.type.Violation.OVERLINE
import rule.wrapper.point.Point

class BudoolRenjuRuleAdapter(
    boardSize: BoardSize,
) : RenjuRule {
    private val budoolBlackRenjuRule: OmokRule = BlackRenjuRule(boardSize.value, boardSize.value)
    private val normalOmokRule = NormalOmokRule(boardSize.value)

    override fun checkLastBlackStoneFoul(board: Board): RenjuFoul {
        val stonesMap = board.stonesMap
        val blackPoints = getPoints(stonesMap, StoneState.BLACK)
        val whitePoints = getPoints(stonesMap, StoneState.WHITE)
        val lastStone = board.lastStone ?: return RenjuFoul.SAFE
        val lastPoint = positionToPoint(lastStone.position)
        val violation = budoolBlackRenjuRule.checkAnyFoulCondition(blackPoints, whitePoints, lastPoint)

        return when (violation) {
            DOUBLE_THREE -> RenjuFoul.THREE_BY_THREE_FOUL
            DOUBLE_FOUR -> RenjuFoul.FOUR_BY_FOUR_FOUL
            OVERLINE -> RenjuFoul.OVER_FIVE_FOUL
            NONE -> RenjuFoul.SAFE
        }
    }

    private fun positionToPoint(position: Position): Point = Point(position.row.value, position.col.value)

    private fun getPoints(
        stonesMap: Map<Position, StoneState>,
        targetState: StoneState,
    ): List<Point> =
        stonesMap
            .filter {
                it.value == targetState
            }.map {
                it.key.toPoint()
            }

    override fun isLastStoneOmok(board: Board): Boolean {
        val stonesMap = board.stonesMap
        val lastStone = board.lastStone ?: return false
        return normalOmokRule.isPositionOmok(stonesMap, lastStone.position)
    }
}
