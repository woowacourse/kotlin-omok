package omok.model.rule

import omok.model.board.Board
import omok.model.position.Direction
import omok.model.position.Position
import omok.model.stone.StoneType

object OmokChecker {
    private const val DEFAULT_STONE_COUNT = 1
    private const val OMOK_PRECONDITION = 5

    fun findOmok(
        lastPosition: Position,
        stoneType: StoneType,
    ): Boolean {
        return Direction.types.any { direction ->
            checkWinningPosition(lastPosition, direction, stoneType)
        }
    }

    private fun checkWinningPosition(
        lastPosition: Position,
        direction: Direction,
        stoneType: StoneType,
    ): Boolean {
        var stoneCount = DEFAULT_STONE_COUNT

        stoneCount += countSameStones(lastPosition, direction, stoneType)

        stoneCount += countSameStones(lastPosition, -direction, stoneType)

        return stoneCount >= OMOK_PRECONDITION
    }

    private fun countSameStones(
        lastPosition: Position,
        direction: Direction,
        stoneType: StoneType,
    ): Int {
        var sameStoneCount = 0
        val rowDirection = direction.row.value
        val columnDirection = direction.column.value
        var row = lastPosition.row.value + rowDirection
        var column = lastPosition.column.value + columnDirection

        while (row in Board.axisRange && column in Board.axisRange && Board.board[column][row] == stoneType) {
            sameStoneCount++
            row += rowDirection
            column += columnDirection
        }

        return sameStoneCount
    }
}
