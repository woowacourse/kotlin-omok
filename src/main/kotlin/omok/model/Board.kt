package omok.model

class Board private constructor(
    private val stones: List<List<StoneState>> = emptyList(),
    private val lastStoneState: StoneState = StoneState.NONE,
) {
    fun placeStone(stone: Stone): Board {
        if (stonePlaced(stone.position)) {
            throw IllegalStateException()
        }
        check(stone.stoneState != lastStoneState) { "같은 색의 돌을 연속하여 착수할 수 없습니다" }
        val (newStoneX, newStoneY) = stone.position

        val newStones: List<List<StoneState>> =
            stones.mapIndexed { y, row ->
                if (y == newStoneY.value) {
                    row.mapIndexed { x, state ->
                        if (x == newStoneX.value) stone.stoneState else state
                    }
                } else {
                    row
                }
            }
        return Board(newStones, stone.stoneState)
    }

    private fun stonePlaced(position: Position): Boolean {
        val (x, y) = position.row to position.col

        return (stones[x.value][y.value] != StoneState.NONE)
    }

    private fun stonePlacedState(position: Position): StoneState {
        val (x, y) = position.row to position.col

        return stones[x.value][y.value]
    }

    private fun countConnected(
        position: Position,
        stoneState: StoneState,
        direction: Direction,
    ): Int {
        var count = 1
        var x = position.row.value + direction.dx
        var y = position.col.value + direction.dy

        while (x in 0 until BOARD_SIZE && y in 0 until BOARD_SIZE) {
            if (stonePlacedState(Position(Row(x), Col(y))) == stoneState) {
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
            val initStones: List<List<StoneState>> =
                List(BOARD_SIZE) {
                    List(BOARD_SIZE) { StoneState.NONE }
                }
            return Board(initStones)
        }

        fun customBoard(stones: Collection<Stone>): Board {
            val emptyBoard = MutableList(BOARD_SIZE) { MutableList(BOARD_SIZE) { StoneState.NONE } }

            stones.forEach { stone ->
                val row = stone.position.row.value
                val col = stone.position.col.value

                if (row in 0 until BOARD_SIZE && col in 0 until BOARD_SIZE) {
                    emptyBoard[row][col] = stone.stoneState
                }
            }

            val lastStoneState = stones.lastOrNull()?.stoneState ?: StoneState.NONE
            return Board(emptyBoard.map { it.toList() }, lastStoneState)
        }
    }
}
