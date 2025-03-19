package omok.model

class Board private constructor(
    val stonesMap: Map<Position, StoneState> = emptyMap(),
    val lastStone: Stone? = null,
) {
    val nextStoneState: StoneState
        get() {
            lastStone?.let {
                return when (lastStone.stoneState) {
                    StoneState.BLACK -> StoneState.WHITE
                    StoneState.WHITE -> StoneState.BLACK
                    StoneState.NONE -> StoneState.NONE
                }
            } ?: run {
                return StoneState.BLACK
            }
        }

    fun placeStone(nextPosition: Position): Board {
        val nextStone = Stone(nextPosition, nextStoneState)
        require(!stonesMap.containsKey(nextStone.position)) { "해당하는 위치에 돌이 존재합니다" }
        require(lastStone == null || (nextStone.stoneState != (lastStone.stoneState))) { "같은 색의 돌을 연속하여 착수할 수 없습니다" }

        val newBoardStones = stonesMap + (nextStone.position to nextStone.stoneState)

        return Board(newBoardStones, nextStone)
    }

    private fun stonePlacedState(position: Position): StoneState = stonesMap[position] ?: StoneState.NONE

    private fun countConnected(
        position: Position,
        stoneState: StoneState,
        direction: Direction,
    ): Int {
        var count = 1
        var x = position.row.value + direction.dx
        var y = position.col.value + direction.dy

        while (x in 0 until BOARD_SIZE && y in 0 until BOARD_SIZE) {
            val nextPos = Position(Row(x), Col(y))
            if (stonesMap[nextPos] == stoneState) {
                count++
                x += direction.dx
                y += direction.dy
            } else {
                break
            }
        }
        return count
    }

    fun isOmok(position: Position): Boolean {
        if (stonePlacedState(position) == StoneState.NONE) {
            return false
        }

        val stoneState = stonePlacedState(position)

        for (i in Direction.entries.indices step 2) { // (1,2)계산, (3,4)계산
            val dir1 = Direction.entries[i] // 정방향 1, 3
            val dir2 = Direction.entries[i + 1] // 반대방향 2, 4

            val count1 = countConnected(position, stoneState, dir1)
            val count2 = countConnected(position, stoneState, dir2)
            val totalCount = count1 + count2 + 1
            if (totalCount >= 5) return true
        }
        return false
    }

    companion object {
        private const val BOARD_SIZE = 15

        fun initBoard(): Board {
            val initStonesMap: Map<Position, StoneState> = emptyMap()
            return Board(initStonesMap)
        }

        fun customBoard(stones: Collection<Stone>): Board {
            val board = mutableMapOf<Position, StoneState>()
            val lastStone = stones.last()
            stones.forEach { stone ->
                val row = stone.position.row.value
                val col = stone.position.col.value

                if (row in 0 until BOARD_SIZE && col in 0 until BOARD_SIZE) {
                    board[stone.position] = stone.stoneState
                }
            }

            return Board(board, lastStone)
        }
    }
}
