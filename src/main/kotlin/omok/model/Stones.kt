package omok.model

class Stones(initialStones: List<Stone> = emptyList()) {
    private val _stones: MutableList<Stone> = mutableListOf()
    val stones: List<Stone>
        get() = _stones

    init {
        _stones.addAll(initialStones)
    }

    fun putStone(stone: Stone) {
        val isOccupied = checkOccupiedCoordinate(stone.coordinate)
        when (isOccupied) {
            false -> _stones.add(stone)
            true -> throw IllegalStateException(ERROR_CANT_PUT_STONE)
        }
    }

    private fun checkOccupiedCoordinate(coordinate: Coordinate): Boolean {
        return stones.any { it.coordinate == coordinate }
    }

    fun findOmok(stone: Stone): Boolean {
        for (direction in Directions) {
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
        count += countSameColorStonesInOneDirection(startStone, direction)
        count += countSameColorStonesInOneDirection(startStone, direction.invert())
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
        const val ERROR_CANT_PUT_STONE = "이미 돌이 착수된 위치입니다."
        val OMOK_COUNT: Int = 5
        val Directions =
            listOf(
                // TOP,TOP_RIGHT,RIGHT,RIGHT_BOTTOM
                Direction(1, 0),
                Direction(1, 1),
                Direction(0, 1),
                Direction(-1, 1),
            )
    }
}
