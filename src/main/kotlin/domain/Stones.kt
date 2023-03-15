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
            val score = startSearch(stone.coordinate + item, item, 0)
            val invertedScore = startSearch(stone.coordinate + invertedDirections[index], invertedDirections[index], 0)
            if (score + invertedScore >= WINNING_CONDITION) return true
        }
        return false
    }

    private fun startSearch(coordinate: Coordinate, direction: Point, count: Int): Int {
        if (_value.any { it.coordinate == coordinate })
            return startSearch(coordinate + direction, direction, count + 1)
        return count
    }

    companion object {
        const val MESSAGE_CORRUPT_STONE = "같은 위치에 이미 돌이 있습니다. 위치 : (%d, %d)"
        private val directions = listOf(
            Point(-1, 1), Point(0, 1), Point(1, 1), Point(1, 0)
        )
        private val invertedDirections = listOf(Point(1, -1), Point(0, -1), Point(-1, -1), Point(-1, 0))
        private const val WINNING_CONDITION = 4
    }
}
