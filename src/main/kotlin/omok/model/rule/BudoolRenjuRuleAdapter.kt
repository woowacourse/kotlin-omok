package omok.model.rule

import omok.model.board.Board
import omok.model.board.BoardSize
import omok.model.stone.Stone
import omok.model.stone.StoneColor
import omok.model.stone.position.Position
import rule.facade.BlackRenjuRule

class BudoolRenjuRuleAdapter(
    boardSize: BoardSize,
) : RenjuRule {
    private val normalOmokRule = NormalOmokRule(boardSize.value)
    private val blackRenjuRule = BlackRenjuRule(boardSize.value, boardSize.value)

    override fun checkLastBlackStoneFoul(
        stonesMap: Map<Position, StoneColor>,
        lastStone: Stone,
    ): RenjuFoul {
        val blackCoordinatePairs = getTargetStoneCoordinatePairs(stonesMap, StoneColor.BLACK)
        val whiteCoordinatePairs = getTargetStoneCoordinatePairs(stonesMap, StoneColor.WHITE)
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
        stonesMap: Map<Position, StoneColor>,
        targetColor: StoneColor,
    ): List<Pair<Int, Int>> =
        stonesMap
            .filter {
                it.value == targetColor
            }.map {
                positionToCoordinatePair(it.key)
            }

    private fun positionToCoordinatePair(position: Position): Pair<Int, Int> =
        position.row.value + BUDOOL_LIBRARY_INDEX_OFFSET to position.col.value + BUDOOL_LIBRARY_INDEX_OFFSET

    override fun isOmok(board: Board): Boolean {
        val stonesMap = board.stonesMap
        val lastStone = board.lastStone ?: return false
        return normalOmokRule.isPositionOmok(stonesMap, lastStone.position)
    }

    companion object {
        private const val BUDOOL_LIBRARY_INDEX_OFFSET = 1
    }
}
