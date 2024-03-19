package omok.model

class Board {
    private val board = Array(BOARD_SIZE) { Array(BOARD_SIZE) { 0 } }
    private val _positionInfo: MutableList<Pair<Int, Int>> = mutableListOf()
    val positionInfo: List<Pair<Int, Int>>
        get() = _positionInfo.toList()

    fun putStone(position: Position) {
        board[position.row][position.col] = 1
    }

    fun judgeWinning(
        startPosition: Position,
        stone: Int,
    ): Boolean {
        // 이 함수에서, 주어진 위치에 실제로 player의 돌이 있는지 확인
        if (board[startPosition.row][startPosition.col] != stone) return false

        return findOmok(startPosition, stone)
    }

    private fun findOmok(
        startPosition: Position,
        stone: Int,
    ) = checkWinningPosition(startPosition, horizontalDirection, stone) || // 수평 방향
        checkWinningPosition(startPosition, verticalDirection, stone) || // 수직 방향
        checkWinningPosition(startPosition, upwardDirection, stone) || // 대각선 (\) 방향
        checkWinningPosition(startPosition, downwardDirection, stone) // 대각선 (/) 방향

    private fun checkWinningPosition(
        startPosition: Position,
        deltaPosition: DeltaPosition,
        stone: Int,
    ): Boolean {
        var stoneCount = DEFAULT_STONE_COUNT // 현재 돌을 포함해야 하므로 1부터 시작

        // 한 방향으로 이동하며 같은 돌을 세기
        stoneCount += countSameStones(startPosition, deltaPosition, stone)

        // 반대 방향으로 이동하며 같은 돌 세기
        stoneCount += countSameStones(startPosition, deltaPosition, stone)

        return stoneCount >= OMOK_PRECONDITION // 5개 이상 연속되면 승리
    }

    private fun countSameStones(
        startPosition: Position,
        deltaPosition: DeltaPosition,
        stone: Int,
    ): Int {
        var sameStoneCount = 0
        var row = startPosition.row + deltaPosition.deltaRow
        var col = startPosition.col + deltaPosition.deltaCol

        while (row in 0 until BOARD_SIZE && col in 0 until BOARD_SIZE && board[row][col] == stone) {
            sameStoneCount++
            row += deltaPosition.deltaRow
            col += deltaPosition.deltaCol
        }

        return sameStoneCount
    }

    companion object {
        const val BOARD_SIZE = 15
        private const val DEFAULT_STONE_COUNT = 1
        private const val OMOK_PRECONDITION = 5
        private val horizontalDirection = DeltaPosition(0, 1)
        private val verticalDirection = DeltaPosition(1, 0)
        private val upwardDirection = DeltaPosition(1, 1)
        private val downwardDirection = DeltaPosition(1, -1)
    }
}
