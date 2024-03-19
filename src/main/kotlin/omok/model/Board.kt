package omok.model

object Board {
    private const val BOARD_SIZE = 15
    private const val DEFAULT_STONE_COUNT = 1
    private const val OMOK_PRECONDITION = 5
    private val horizontalDirection = DeltaPosition(0, 1)
    private val verticalDirection = DeltaPosition(1, 0)
    private val upwardDirection = DeltaPosition(1, 1)
    private val downwardDirection = DeltaPosition(1, -1)

    val board = Array(BOARD_SIZE) { Array(BOARD_SIZE) { Stone.NONE } }

    fun findOmok(
        lastPosition: Position,
        stone: Stone,
    ) = checkWinningPosition(lastPosition, horizontalDirection, stone) ||
        checkWinningPosition(lastPosition, verticalDirection, stone) ||
        checkWinningPosition(lastPosition, upwardDirection, stone) ||
        checkWinningPosition(lastPosition, downwardDirection, stone)

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
        var row = lastPosition.row + deltaPosition.deltaRow
        var col = lastPosition.col + deltaPosition.deltaCol

        while (row in 0 until BOARD_SIZE && col in 0 until BOARD_SIZE && board[row][col] == stone) {
            sameStoneCount++
            row += deltaPosition.deltaRow
            col += deltaPosition.deltaCol
        }

        return sameStoneCount
    }
}
