package domain

class RenjuRule : RenjuRuleInterface { // 원시값을 받는다고 생각한다?
    override fun isThreeToThree(stone: Stone, stones: Stones): Boolean {
        return directions.sumOf { direction ->
            checkOpenFourForLine((direction * -4) + stone.coordinate.point, direction, stones)
        } >= RENJU_LINE_CONDITION
    }

    private fun checkOpenFourForLine(start: Point, direction: Point, stones: Stones): Int {
        return OPEN_FOUR_INNER_BLOCK_RANGE.count {
            isOpenFour(start + (direction * it), direction, stones)
        }
    }

    private fun isOpenFour(start: Point, direction: Point, stones: Stones): Boolean {
        if (!validateCheckBlock(start, direction, OPEN_FOUR_BLOCK_SIZE)) return false

        val isOpened =
            stones.value.none { it.coordinate.point == start } && stones.value.none { it.coordinate.point == (start + (direction * OPEN_FOUR_BLOCK_END)) }
        val isFour = OPEN_FOUR_INNER_BLOCK_SHIFTED_RANGE.count {
            isBlackStoneThere(start + (direction * it), stones)
        } == OPEN_FOUR_BLACK_STONE_COUNT
        return isOpened && isFour
    }

    override fun isFourToFour(stone: Stone, stones: Stones): Boolean {
        return fullDirections.count { direction ->
            checkFourForLine((direction * -4) + stone.coordinate.point, direction, stones)
        } >= RENJU_LINE_CONDITION
    }

    private fun checkFourForLine(start: Point, direction: Point, stones: Stones): Boolean {
        return FOUR_BLOCK_RANGE.any {
            isFour(start + (direction * it), direction, stones)
        }
    }

    private fun isFour(start: Point, direction: Point, stones: Stones): Boolean {
        if (!validateCheckBlock(start, direction, FOUR_BLOCK_SIZE)) return false

        return FOUR_BLOCK_SIZE_RANGE.count {
            isBlackStoneThere(start + (direction * it), stones)
        } >= FOUR_BLACK_STONE_COUNT
    }

    private fun validateCheckBlock(start: Point, direction: Point, size: Int): Boolean {
        repeat(size) {
            val next = (direction * it)
            val currentX = start.x + next.x
            val currentY = start.y + next.y
            if (currentX < Board.BOARD_START_POINT.x || currentY < Board.BOARD_START_POINT.y ||
                currentX >= Board.BOARD_END_POINT.x || currentY >= Board.BOARD_END_POINT.y
            ) return false
        }
        return true
    }

    private fun isBlackStoneThere(start: Point, stones: Stones): Boolean {
        val stone = stones.value.find { it.coordinate.point == start }
        if (stone == null || stone.color == Color.WHITE) return false
        else if (stone.color == Color.BLACK) return true
        return false
    }

    override fun isOverFive(stoneScore: Int): Boolean = stoneScore <= LARGE_PLACE

    companion object {
        private const val RENJU_LINE_CONDITION = 2
        private val OPEN_FOUR_INNER_BLOCK_RANGE = (0..3)
        private val FOUR_BLOCK_RANGE = (0..2)
        private val FOUR_BLOCK_SIZE_RANGE = (0..4)
        private val OPEN_FOUR_INNER_BLOCK_SHIFTED_RANGE = (1..4)
        private const val OPEN_FOUR_BLOCK_SIZE = 6
        private const val FOUR_BLOCK_SIZE = 5
        private const val OPEN_FOUR_BLOCK_END = 5
        private const val OPEN_FOUR_BLACK_STONE_COUNT = 2
        private const val FOUR_BLACK_STONE_COUNT = 3

        private const val LARGE_PLACE = 5

        val directions = listOf(
            Point(-1, 1), Point(0, 1), Point(1, 1), Point(1, 0)
        )

        val fullDirections = listOf(
            Point(-1, 1), Point(0, 1), Point(1, 1), Point(1, 0),
            Point(1, -1), Point(0, -1), Point(-1, -1), Point(-1, 0)
        )
    }
}
