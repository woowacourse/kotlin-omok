package domain

class Stones(value: List<Stone> = listOf()) {
    private val _value: MutableList<Stone> = value.toMutableList()
    val renjuRule = RenjuRule(value)
    val value: List<Stone>
        get() = _value.toList()

    fun place(stone: Stone) {
        require(value.none { it.coordinate == stone.coordinate }) {
            MESSAGE_CORRUPT_STONE.format(stone.coordinate.x, stone.coordinate.y)
        }
        _value.add(stone)
    }

    fun isWinPlace(stone: Stone): Boolean {
        return findScore(stone) >= WINNING_CONDITION
    }

    fun findScore(stone: Stone): Int {
        return directions.maxOf { item ->
            val nextCoordinate = (stone.coordinate + item) ?: return@maxOf 0
            val score = startSearch(nextCoordinate, item, stone.color, INITIAL_SCORE)
            val invertedDirections = item * -1
            val invertCoordinate = (stone.coordinate + invertedDirections) ?: return@maxOf 0
            val invertedScore = startSearch(invertCoordinate, invertedDirections, stone.color, INITIAL_SCORE)
            score + invertedScore
        }
    }

    private fun startSearch(coordinate: Coordinate, direction: Point, color: Color, count: Int): Int {
        if (_value.any { it.coordinate == coordinate && it.color == color }) {
            val nextCoordinate = (coordinate + direction) ?: return count + SEARCH_INTERVAL
            return startSearch(nextCoordinate, direction, color, count + SEARCH_INTERVAL)
        }
        return count
    }

    companion object {
        val directions = listOf(
            Point(-1, 1), Point(0, 1), Point(1, 1), Point(1, 0)
        )
        private const val WINNING_CONDITION = 4
        private const val INITIAL_SCORE = 0
        private const val SEARCH_INTERVAL = 1

        private const val MESSAGE_CORRUPT_STONE = "같은 위치에 이미 돌이 있습니다. 위치 : (%d, %d)"
    }
}
