package omok.domain

class Board(private val rule: Rule) {
    val stones: Stones = Stones()
    val grid: Array<Array<StoneType>> = Array(BOARD_SIZE) { Array(BOARD_SIZE, { StoneType.EMPTY }) }

    fun put(stone: Stone) {
        val row = stone.position.row
        val column = stone.position.column
        validateStoneRange(stone)
        isValidPosition(stone)
        if (grid[row][column] != StoneType.EMPTY) {
            throw IllegalArgumentException(ERROR_STONE_ALREADY_PLACED)
        }
        stones.add(stone)
        grid[row][column] = stone.color
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

    private fun isValidPosition(stone: Stone): Boolean {
        return if (stone.color == StoneType.BLACK) {
            !rule.isInvalid(stones, stone, grid)
        } else {
            true
        }
    }

    private fun validateStoneRange(stone: Stone) {
        if (stone.position.row !in 0 until BOARD_SIZE || stone.position.column !in 0 until BOARD_SIZE) {
            throw IllegalArgumentException(ERROR_INVALID_PLACED.format(BOARD_SIZE))
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
        private const val BOARD_SIZE = 15
        private const val ERROR_STONE_ALREADY_PLACED = "이미 돌이 놓여진 위치입니다. 다시 입력해주세요."
        private const val ERROR_INVALID_PLACED = "유효하지 않은 돌의 위치입니다. 오목판은 0 이상 %d 미만이어야 합니다."
        private val directions = listOf(listOf(1, 0), listOf(1, 1), listOf(0, 1), listOf(1, -1))
        private const val OMOK_WINNING_CONDITION = 5
    }
}
