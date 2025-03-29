package woowacourse.omok.domain

class Board(private val rule: Rule) {
    val stones: Stones = Stones()
    val grid: Array<Array<StoneType>> = Array(BOARD_SIZE) { Array(BOARD_SIZE) { StoneType.EMPTY } }

    fun put(
        position: Position,
        color: StoneType,
    ): Stone {
        val stone = Stone(position, color)
//        isValidBlackPosition(stone)
//        isValidPosition(stone.position)
        stones.add(stone)
        grid[position.row][position.column] = stone.color
        return stone
    }

    fun isFull(): Boolean = grid.all { row -> row.all { column -> column != StoneType.EMPTY } }

    fun clear() {
        for (row in grid.indices) {
            for (column in grid[row].indices) {
                grid[row][column] = StoneType.EMPTY
            }
        }
        stones.reset()
    }

    fun isInvalidBlackPosition(stone: Stone): Boolean {
        return (stone.color == StoneType.BLACK && rule.isInvalid(stones, stone, grid))
    }

    fun isInvalidPosition(position: Position): Boolean {
        return grid[position.row][position.column] != StoneType.EMPTY
    }

    companion object {
        const val BOARD_SIZE = 15
        private const val ERROR_STONE_ALREADY_PLACED = "이미 돌이 놓여진 위치입니다. 다시 입력해주세요."
        private const val ERROR_INVALID_BLACK_STONE = "흑돌이 놓을 수 없는 금수 위치입니다. 다시 입력해주세요."
    }
}
