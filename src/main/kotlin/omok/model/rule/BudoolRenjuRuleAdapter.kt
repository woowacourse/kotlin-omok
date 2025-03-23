package omok.model.rule

import omok.model.board.Board
import omok.model.board.BoardSize
import omok.model.stone.StoneState
import omok.model.stone.position.Position
import rule.facade.BlackRenjuRule

class BudoolRenjuRuleAdapter(
    boardSize: BoardSize,
) : RenjuRule {
    private val normalOmokRule = NormalOmokRule(boardSize.value)
    private val blackRenjuRule = BlackRenjuRule(boardSize.value, boardSize.value)

    override fun checkLastBlackStoneFoul(board: Board): RenjuFoul {
        val stonesMap = board.stonesMap
        val blackCoordinatePairs = getTargetStoneCoordinatePairs(stonesMap, StoneState.BLACK)
        val whiteCoordinatePairs = getTargetStoneCoordinatePairs(stonesMap, StoneState.WHITE)
        val lastStone = board.lastStone ?: return RenjuFoul.SAFE
        val lastPoint = positionToCoordinatePair(lastStone.position)
        when {
            normalOmokRule.isPositionOmok(stonesMap, lastStone.position, true) -> return RenjuFoul.SAFE

            blackRenjuRule.checkDoubleThreeFoul(
                blackCoordinatePairs,
                whiteCoordinatePairs,
                lastPoint,
            ) -> return RenjuFoul.THREE_BY_THREE_FOUL

            blackRenjuRule.checkDoubleFourFoul(
                blackCoordinatePairs,
                whiteCoordinatePairs,
                lastPoint,
            ) -> return RenjuFoul.FOUR_BY_FOUR_FOUL

            blackRenjuRule.checkOverline(
                blackCoordinatePairs,
                lastPoint,
            ) -> return RenjuFoul.OVER_FIVE_FOUL

            else -> return RenjuFoul.SAFE
        }
    }

    private fun getTargetStoneCoordinatePairs(
        stonesMap: Map<Position, StoneState>,
        targetState: StoneState,
    ): List<Pair<Int, Int>> =
        stonesMap
            .filter {
                it.value == targetState
            }.map {
                positionToCoordinatePair(it.key)
            }

    private fun positionToCoordinatePair(position: Position): Pair<Int, Int> = position.row.value + 1 to position.col.value + 1

    override fun isLastStoneOmok(board: Board): Boolean {
        val stonesMap = board.stonesMap
        val lastStone = board.lastStone ?: return false
        return normalOmokRule.isPositionOmok(stonesMap, lastStone.position)
    }
}
