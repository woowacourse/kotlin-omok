package domain

class Stones(value: List<Stone> = listOf()) {
    private val _value: MutableList<Stone> = value.toMutableList()
    val value: List<Stone>
        get() = _value.toList()

    fun place(stone: Stone) {
        require(value.none { it.coordinate == stone.coordinate }) {
            MESSAGE_CORRUPT_STONE.format(stone.coordinate.x, stone.coordinate.y)
        }
        _value.add(stone)
    }

    fun isWinPlace(stone: Stone): Boolean {
        directions.forEachIndexed { index, item ->
            val nextCoordinate = (stone.coordinate + item) ?: return@forEachIndexed
            val score = startSearch(nextCoordinate, item, stone.color, INITIAL_SCORE)
            val invertCoordinate = (stone.coordinate + invertedDirections[index]) ?: return@forEachIndexed
            val invertedScore = startSearch(invertCoordinate, invertedDirections[index], stone.color, INITIAL_SCORE)
            println(score + invertedScore)
            if (score + invertedScore >= WINNING_CONDITION) return true
        }
        return false
    }

    private fun startSearch(coordinate: Coordinate, direction: Point, color: Color, count: Int): Int {
        if (_value.any { it.coordinate == coordinate && it.color == color }) {
            val nextCoordinate = (coordinate + direction) ?: return count + SEARCH_INTERVAL
            return startSearch(nextCoordinate, direction, color, count + SEARCH_INTERVAL)
        }
        return count
    }

    fun threeToThree(stone: Stone): Boolean {
        return directions.count { direction ->
            checkOpenFourForLine((direction * -4) + stone.coordinate.point, direction)
        } >= 2
    }

    private fun checkOpenFourForLine(start: Point, direction: Point): Boolean {
        for (i in 0..3) {
            if (isOpenFour(start + (direction * i), direction)) return true
        }
        return false
    }

    private fun isOpenFour(start: Point, direction: Point): Boolean {
        repeat(5) {
            val next = (direction * it)
            if (Coordinate.from(start.x + next.x, start.y + next.y) == null)
                return false
        }

        val isOpened =
            _value.none { it.coordinate.point == start } && _value.none { it.coordinate.point == (start + (direction * 5)) }
        var blackStoneCount = 0
        for (i in 1..4) {
            val stone = _value.find {
                it.coordinate.point == start + (direction * i)
            }
            if (stone == null) continue
            else if (stone.color == Color.BLACK) blackStoneCount++
            else if (stone.color == Color.WHITE) return false
        }
        val isFour = blackStoneCount == 2
        return isOpened && isFour
    }

    companion object {
        private val directions = listOf(
            Point(-1, 1), Point(0, 1), Point(1, 1), Point(1, 0)
        )
        private val invertedDirections = listOf(Point(1, -1), Point(0, -1), Point(-1, -1), Point(-1, 0))
        private const val WINNING_CONDITION = 4
        private const val INITIAL_SCORE = 0
        private const val SEARCH_INTERVAL = 1

        private const val MESSAGE_CORRUPT_STONE = "같은 위치에 이미 돌이 있습니다. 위치 : (%d, %d)"
    }
}
