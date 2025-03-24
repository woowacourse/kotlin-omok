package omok.domain

class Board(private val rule: Rule) {
    val stones: Stones = Stones()
    val grid: Array<Array<StoneType>> = Array(BOARD_SIZE) { Array(BOARD_SIZE) { StoneType.EMPTY } }

    fun put(
        position: Position,
        color: StoneType,
    ): Stone {
        val stone = Stone(position, color)
        isValidPosition(stone)
        if (grid[position.row][position.column] != StoneType.EMPTY) {
            throw IllegalArgumentException(ERROR_STONE_ALREADY_PLACED)
        }
        stones.add(stone)
        grid[position.row][position.column] = stone.color
        return stone
    }

    fun isOmok(stone: Stone): Boolean {
        directions.forEach { direction ->
            var count = 1

            count += stonesCount(stone, direction)
            count += stonesCount(stone, listOf(-direction[0], -direction[1]))

            if (count >= OMOK_WINNING_CONDITION) return true
        }
        return false
    }

    fun isFull(): Boolean = grid.all { row -> row.all { column -> column != StoneType.EMPTY } }

    private fun isValidPosition(stone: Stone) {
        if (stone.color == StoneType.BLACK && rule.isInvalid(stones, stone, grid)) {
            throw IllegalArgumentException(ERROR_INVALID_BLACK_STONE)
        }
    }

    private fun stonesCount(
        stone: Stone,
        direction: List<Int>,
    ): Int {
        var currentPosition = Pair(stone.position.row + direction[0], stone.position.column + direction[1])
        var count = 0

        while (currentPosition.first in 0 until BOARD_SIZE && currentPosition.second in 0 until BOARD_SIZE &&
            stones.stones.any { it == Stone(Position(currentPosition.first, currentPosition.second), stone.color) }
        ) {
            count++
            currentPosition = Pair(currentPosition.first + direction[0], currentPosition.second + direction[1])
        }
        return count
    }

    companion object {
        const val BOARD_SIZE = 15
        private const val ERROR_STONE_ALREADY_PLACED = "이미 돌이 놓여진 위치입니다. 다시 입력해주세요."
        private const val ERROR_INVALID_BLACK_STONE = "흑돌이 놓을 수 없는 금수 위치입니다. 다시 입력해주세요."
        private val directions = listOf(listOf(1, 0), listOf(1, 1), listOf(0, 1), listOf(1, -1))
        private const val OMOK_WINNING_CONDITION = 5
    }
}
