package domain

class RenjuRule(override val stones: Stones) : OmokRule {
    override fun isThreeToThree(stone: Stone): Boolean {
        return directions.sumOf { direction ->
            checkOpenFourForLine((direction * -4) + stone.coordinate.vector, direction)
        } >= RENJU_LINE_CONDITION
    }

    private fun checkOpenFourForLine(start: Vector, direction: Vector): Int {
        return OPEN_FOUR_INNER_BLOCK_RANGE.count {
            isOpenFour(start + (direction * it), direction)
        }
    }

    private fun isOpenFour(start: Vector, direction: Vector): Boolean {
        if (!validateCheckBlock(start, direction, OPEN_FOUR_BLOCK_SIZE)) return false

        val isOpened =
            stones.value.none { it.coordinate.vector == start } && stones.value.none { it.coordinate.vector == (start + (direction * OPEN_FOUR_BLOCK_END)) }
        val isFour = OPEN_FOUR_INNER_BLOCK_SHIFTED_RANGE.count {
            isBlackStoneThere(start + (direction * it))
        } == OPEN_FOUR_BLACK_STONE_COUNT
        return isOpened && isFour
    }

    override fun isFourToFour(stone: Stone): Boolean {
        return fullDirections.count { direction ->
            checkFourForLine((direction * -4) + stone.coordinate.vector, direction)
        } >= RENJU_LINE_CONDITION
    }

    private fun checkFourForLine(start: Vector, direction: Vector): Boolean {
        return FOUR_BLOCK_RANGE.any {
            isFour(start + (direction * it), direction)
        }
    }

    private fun isFour(start: Vector, direction: Vector): Boolean {
        if (!validateCheckBlock(start, direction, FOUR_BLOCK_SIZE)) return false

        return FOUR_BLOCK_SIZE_RANGE.count {
            isBlackStoneThere(start + (direction * it))
        } >= FOUR_BLACK_STONE_COUNT
    }

    private fun validateCheckBlock(start: Vector, direction: Vector, size: Int): Boolean {
        repeat(size) {
            val next = (direction * it)
            if (Coordinate.from(start.x + next.x, start.y + next.y) == null) return false
        }
        return true
    }

    private fun isBlackStoneThere(start: Vector): Boolean {
        val stone = stones.value.find { it.coordinate.vector == start }
        if (stone == null || stone.color is Color.White) return false
        else if (stone.color is Color.Black) return true
        return false
    }

    override fun findScore(stone: Stone): Int {
        return directions.maxOf { item ->
            val nextCoordinate = (stone.coordinate + item) ?: return@maxOf 0
            val score = startSearch(nextCoordinate, item, stone.color, INITIAL_SCORE)
            val invertedDirections = item * -1
            val invertCoordinate = (stone.coordinate + invertedDirections) ?: return@maxOf 0
            val invertedScore = startSearch(invertCoordinate, invertedDirections, stone.color, INITIAL_SCORE)
            score + invertedScore
        }
    }

    private fun startSearch(coordinate: Coordinate, direction: Vector, color: Color, count: Int): Int {
        if (stones.value.any { it.coordinate == coordinate && it.color == color }) {
            val nextCoordinate = (coordinate + direction) ?: return count + SEARCH_INTERVAL
            return startSearch(nextCoordinate, direction, color, count + SEARCH_INTERVAL)
        }
        return count
    }

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

        private const val INITIAL_SCORE = 0
        private const val SEARCH_INTERVAL = 1

        private val directions = listOf(
            Vector(-1, 1), Vector(0, 1), Vector(1, 1), Vector(1, 0)
        )

        private val fullDirections = listOf(
            Vector(-1, 1), Vector(0, 1), Vector(1, 1), Vector(1, 0),
            Vector(1, -1), Vector(0, -1), Vector(-1, -1), Vector(-1, 0)
        )
    }
}
