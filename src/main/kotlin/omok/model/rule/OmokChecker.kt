package omok.model.rule

import omok.model.board.Board
import omok.model.position.DeltaPosition
import omok.model.position.Position
import omok.model.stone.Stone

object OmokChecker {
    private const val DEFAULT_STONE_COUNT = 1
    private const val OMOK_PRECONDITION = 5
    private val horizontalDirection = DeltaPosition(0, 1)
    private val verticalDirection = DeltaPosition(1, 0)
    private val upwardDirection = DeltaPosition(1, 1)
    private val downwardDirection = DeltaPosition(1, -1)

    fun findOmok(
        lastPosition: Position,
        stone: Stone,
    ): Boolean {
        val directionType = listOf(horizontalDirection, verticalDirection, upwardDirection, downwardDirection)
        return directionType.any { direction ->
            checkWinningPosition(lastPosition, direction, stone)
        }
    }

    private fun checkWinningPosition(
        lastPosition: Position,
        deltaPosition: DeltaPosition,
        stone: Stone,
    ): Boolean {
        var stoneCount = DEFAULT_STONE_COUNT

        stoneCount += countSameStones(lastPosition, deltaPosition, stone)

        stoneCount += countSameStones(lastPosition, -deltaPosition, stone)

        return stoneCount >= OMOK_PRECONDITION
    }

    private fun countSameStones(
        lastPosition: Position,
        deltaPosition: DeltaPosition,
        stone: Stone,
    ): Int {
        var sameStoneCount = 0
        var row = lastPosition.row.value + deltaPosition.deltaRow
        var col = lastPosition.col.value + deltaPosition.deltaCol

        while (row in 0 until Board.BOARD_SIZE && col in 0 until Board.BOARD_SIZE && Board.board[col][row] == stone) {
            sameStoneCount++
            row += deltaPosition.deltaRow
            col += deltaPosition.deltaCol
        }

        return sameStoneCount
    }
}