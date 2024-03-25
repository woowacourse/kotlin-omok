package omok.model.rule

import omok.model.board.Board
import omok.model.position.Position
import omok.model.position.RelativeDirection
import omok.model.stone.StoneType

object OmokChecker {
    private const val DEFAULT_STONE_COUNT = 1
    private const val OMOK_PRECONDITION = 5

    fun findOmok(
        lastPosition: Position,
        stoneType: StoneType,
    ): Boolean {
        val directionType = RelativeDirection.getRelativeDirections()
        return directionType.any { direction ->
            checkWinningPosition(lastPosition, direction, stoneType)
        }
    }

    private fun checkWinningPosition(
        lastPosition: Position,
        relativeDirection: RelativeDirection,
        stoneType: StoneType,
    ): Boolean {
        var stoneCount = DEFAULT_STONE_COUNT

        stoneCount += countSameStones(lastPosition, relativeDirection, stoneType)

        stoneCount += countSameStones(lastPosition, -relativeDirection, stoneType)

        return stoneCount >= OMOK_PRECONDITION
    }

    private fun countSameStones(
        lastPosition: Position,
        relativeDirection: RelativeDirection,
        stoneType: StoneType,
    ): Int {
        var sameStoneCount = 0
        var row = lastPosition.row + relativeDirection.relativeRowDirection
        var col = lastPosition.column + relativeDirection.relativeColumnDirection

        while (row in 0 until Board.BOARD_SIZE && col in 0 until Board.BOARD_SIZE && Board.board[col][row] == stoneType) {
            sameStoneCount++
            row += relativeDirection.relativeRowDirection
            col += relativeDirection.relativeColumnDirection
        }

        return sameStoneCount
    }
}
