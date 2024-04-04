package woowacourse.omok.model

class Stones(initialStones: List<Stone> = emptyList()) {
    private val _stones: MutableList<Stone> =
        mutableListOf<Stone>().apply {
            addAll(initialStones)
        }
    val stones: List<Stone>
        get() = _stones

    fun putStone(stone: Stone) {
        _stones.add(stone)
    }

    fun findOmok(stone: Stone): Boolean {
        for (direction in Direction.entries) {
            val count = countSameColorStoneInDirection(stone, direction)
            if (count >= OMOK_COUNT) return true
        }
        return false
    }

    fun countSameColorStoneInDirection(
        startStone: Stone,
        direction: Direction,
    ): Int {
        var count = 1
        count += countSameColorStonesInOneDirection(startStone, direction.direction1)
        count += countSameColorStonesInOneDirection(startStone, direction.direction2)
        println(count)
        return count
    }

    private fun countSameColorStonesInOneDirection(
        startStone: Stone,
        direction: DirectionVector,
    ): Int {
        var count = 0
        var row = startStone.coordinate.x + direction.dx
        var col = startStone.coordinate.y + direction.dy
        val color = startStone.color
        while (
            _stones
                .filter { stone -> stone.color == color }
                .any { it.coordinate.x == row && it.coordinate.y == col }
        ) {
            count++
            row += direction.dx
            col += direction.dy
        }
        return count
    }

    fun getLastStoneCoordinate(): Coordinate? {
        if (_stones.isEmpty()) return null
        return _stones.last().coordinate
    }

    companion object {
        val OMOK_COUNT: Int = 5
    }
}
