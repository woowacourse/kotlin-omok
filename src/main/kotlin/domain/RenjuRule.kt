package domain

class RenjuRule(val stones: List<Stone>) {
    fun isThreeToThree(stone: Stone): Boolean {
        return Stones.directions.sumOf { direction ->
            checkOpenFourForLine((direction * -4) + stone.coordinate.point, direction)
        } >= RENJU_LINE_CONDITION
    }

    fun isFourToFour(stone: Stone): Boolean {
        return Stones.directions.sumOf { direction ->
            checkFourForLine((direction * -4) + stone.coordinate.point, direction)
        } >= RENJU_LINE_CONDITION
    }

    private fun checkOpenFourForLine(start: Point, direction: Point): Int {
        return OPEN_FOUR_INNER_BLOCK_RANGE.count {
            isOpenFour(start + (direction * it), direction)
        }
    }

    private fun checkFourForLine(start: Point, direction: Point): Int {
        return FOUR_BLOCK_RANGE.count {
            isFour(start + (direction * it), direction)
        }
    }

    private fun isOpenFour(start: Point, direction: Point): Boolean {
        if (!validateCheckBlock(start, direction, OPEN_FOUR_BLOCK_SIZE)) return false

        val isOpened =
            stones.none { it.coordinate.point == start } && stones.none { it.coordinate.point == (start + (direction * OPEN_FOUR_BLOCK_END)) }
        val isFour = OPEN_FOUR_INNER_BLOCK_SHIFTED_RANGE.count {
            isBlackStoneThere(start + (direction * it))
        } == OPEN_FOUR_BLACK_STONE_COUNT
        return isOpened && isFour
    }

    private fun isFour(start: Point, direction: Point): Boolean {
        if (!validateCheckBlock(start, direction, FOUR_BLOCK_SIZE)) return false

        return FOUR_BLOCK_RANGE.count {
            isBlackStoneThere(start + (direction * it))
        } >= FOUR_BLACK_STONE_COUNT
    }

    private fun validateCheckBlock(start: Point, direction: Point, size: Int): Boolean {
        repeat(size) {
            val next = (direction * it)
            if (Coordinate.from(start.x + next.x, start.y + next.y) == null) return false
        }
        return true
    }

    private fun isBlackStoneThere(start: Point): Boolean {
        val stone = stones.find { it.coordinate.point == start }
        if (stone == null || stone.color == Color.WHITE) return false
        else if (stone.color == Color.BLACK) return true
        return false
    }

    companion object {
        private const val RENJU_LINE_CONDITION = 2
        private val OPEN_FOUR_INNER_BLOCK_RANGE = (0..3)
        private val FOUR_BLOCK_RANGE = (0..4)
        private val OPEN_FOUR_INNER_BLOCK_SHIFTED_RANGE = (1..4)
        private const val OPEN_FOUR_BLOCK_SIZE = 6
        private const val FOUR_BLOCK_SIZE = 5
        private const val OPEN_FOUR_BLOCK_END = 5
        private const val OPEN_FOUR_BLACK_STONE_COUNT = 2
        private const val FOUR_BLACK_STONE_COUNT = 3
    }
}
