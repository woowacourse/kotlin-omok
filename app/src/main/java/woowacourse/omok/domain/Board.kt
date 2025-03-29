package woowacourse.omok.domain

class Board(private val rule: Rule) {
    val stones: Stones = Stones()
    val grid: Array<Array<StoneType>> = Array(BOARD_SIZE) { Array(BOARD_SIZE) { StoneType.EMPTY } }

    fun put(
        position: Position,
        color: StoneType,
    ): Stone {
        val stone = Stone(position, color)
        stones.add(stone)
        grid[position.row][position.column] = stone.color
        return stone
    }

    fun isFull(): Boolean = grid.all { row -> row.all { column -> column != StoneType.EMPTY } }

    fun isInvalidBlackPosition(stone: Stone): Boolean {
        return (stone.color == StoneType.BLACK && rule.isInvalid(stones, stone, grid))
    }

    fun isInvalidPosition(position: Position): Boolean {
        return grid[position.row][position.column] != StoneType.EMPTY
    }

    companion object {
        const val BOARD_SIZE = 15
    }
}
