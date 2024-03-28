package omok.model

class Stones(initialStones: List<Stone> = emptyList()) {
    private val _stones: MutableList<Stone> = mutableListOf()
    val stones: List<Stone>
        get() = _stones

    init {
        _stones.addAll(initialStones)
    }

    fun putStone(stone: Stone) {
        _stones.add(stone)
    }

    fun findOmok(stone: Stone): Boolean {
        for (direction in Direction.biDirection()) {
            val count = countSameColorStoneInDirection(stone, direction)
            if (count >= OMOK_COUNT) return true
        }
        return false
    }

    fun countSameColorStoneInDirection(
        startStone: Stone,
        direction: BiDirection,
    ): Int {
        var count = 1
        count += countSameColorStonesInOneDirection(startStone, direction.direction1)
        count += countSameColorStonesInOneDirection(startStone, direction.direction2)
        return count
    }

    private fun countSameColorStonesInOneDirection(
        startStone: Stone,
        direction: Direction,
    ): Int {
        var count = 0
        var row = startStone.coordinate.x.value + direction.x
        var col = startStone.coordinate.y.value + direction.y
        val color = startStone.color
        while (
            _stones
                .filter { stone -> stone.color == color }
                .any { it.coordinate.x.value == row && it.coordinate.y.value == col }
        ) {
            count++
            row += direction.x
            col += direction.y
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
