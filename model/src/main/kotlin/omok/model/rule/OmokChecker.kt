package omok.model.rule

import omok.model.board.Board
import omok.model.position.Position
import omok.model.position.RelativeDirection
import omok.model.result.PutResult
import omok.model.stone.StoneType

object OmokChecker {
    private const val DEFAULT_COUNT = 0
    private const val DEFAULT_STONE_COUNT = 1
    private const val OMOK_PRECONDITION = 5

    fun findOmok(
        lastPosition: Position,
        stoneType: StoneType,
    ): PutResult {
        val directionType = RelativeDirection.getRelativeDirections()
        if (findOmokAllDirection(directionType, lastPosition, stoneType)) {
            return PutResult.OMOK
        }
        return PutResult.Running
    }

    private fun findOmokAllDirection(
        directionType: List<RelativeDirection>,
        lastPosition: Position,
        stoneType: StoneType,
    ) = directionType.any { direction -> checkWinningPosition(lastPosition, direction, stoneType) }

    private fun checkWinningPosition(
        lastPosition: Position,
        relativeDirection: RelativeDirection,
        stoneType: StoneType,
    ): Boolean =
        (
            DEFAULT_STONE_COUNT +
                countSameStones(lastPosition, relativeDirection, stoneType) +
                countSameStones(lastPosition, -relativeDirection, stoneType)
        ) >= OMOK_PRECONDITION

    private fun countSameStones(
        lastPosition: Position,
        relativeDirection: RelativeDirection,
        stoneType: StoneType,
    ): Int {
        var sameStoneCount = DEFAULT_COUNT
        var row = lastPosition.row + relativeDirection.relativeRowDirection
        var column = lastPosition.column + relativeDirection.relativeColumnDirection

        while (Board.isPositionInRange(row, column) && Board.isSameStone(column, row, stoneType)) {
            sameStoneCount++
            row += relativeDirection.relativeRowDirection
            column += relativeDirection.relativeColumnDirection
        }

        return sameStoneCount
    }
}
