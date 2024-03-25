package omok.model.rule

import omok.model.board.Board
import omok.model.position.DeltaPosition
import omok.model.position.Position
import omok.model.stone.StoneType

object OmokChecker {
    private const val DEFAULT_STONE_COUNT = 1
    private const val OMOK_PRECONDITION = 5
    private val horizontalDirection = DeltaPosition(0, 1)
    private val verticalDirection = DeltaPosition(1, 0)
    private val upwardDirection = DeltaPosition(1, 1)
    private val downwardDirection = DeltaPosition(1, -1)

    fun findOmok(
        lastPosition: Position,
        stoneType: StoneType,
    ): Boolean {
        val directionType = listOf(horizontalDirection, verticalDirection, upwardDirection, downwardDirection)
        return directionType.any { direction ->
            checkWinningPosition(lastPosition, direction, stoneType)
        }
    }

    private fun checkWinningPosition(
        lastPosition: Position,
        deltaPosition: DeltaPosition,
        stoneType: StoneType,
    ): Boolean {
        var stoneCount = DEFAULT_STONE_COUNT

        stoneCount += countSameStones(lastPosition, deltaPosition, stoneType)

        stoneCount += countSameStones(lastPosition, -deltaPosition, stoneType)

        return stoneCount >= OMOK_PRECONDITION
    }

    private fun countSameStones(
        lastPosition: Position,
        deltaPosition: DeltaPosition,
        stoneType: StoneType,
    ): Int {
        var sameStoneCount = 0
        var row = lastPosition.row + deltaPosition.deltaRow
        var col = lastPosition.column + deltaPosition.deltaColumn

        while (row in 0 until Board.BOARD_SIZE && col in 0 until Board.BOARD_SIZE && Board.board[col][row] == stoneType) {
            sameStoneCount++
            row += deltaPosition.deltaRow
            col += deltaPosition.deltaColumn
        }

        return sameStoneCount
    }
}
