package omok.model

class Stones {
    private val _stones: MutableList<Stone> = mutableListOf()
    val stones: List<Stone>
        get() = _stones

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
        direction: List<Int>,
    ): Int {
        var count = 1
        count += countSameColorStonesInOneDirection(startStone, direction.first(), direction.last())
        count += countSameColorStonesInOneDirection(startStone, -direction.first(), -direction.last())
        return count
    }

    private fun countSameColorStonesInOneDirection(
        startStone: Stone,
        dr: Int,
        dc: Int,
    ): Int {
        var count = 0
        var row = startStone.coordinate.row.value + dr
        var col = startStone.coordinate.col.value + dc
        val color = startStone.color
        while (
            _stones
                .filter { stone -> stone.color == color }
                .any { it.coordinate.row.value == row && it.coordinate.col.value == col }
        ) {
            count++
            row += dr
            col += dc
        }
        return count
    }

    fun getLastStoneCoordinate(): Coordinate? {
        if(_stones.isEmpty()) return null
        return _stones.last().coordinate
    }

    companion object {
        const val ERROR_CANT_PUT_STONE = "이미 돌이 착수된 위치입니다."
        val OMOK_COUNT: Int = 5
        val TOP = listOf(1, 0)
        val TOP_RIGHT = listOf(1, 1)
        val RIGHT = listOf(0, 1)
        val RIGHT_BOTTOM = listOf(-1, 1)
        val Directions =
            listOf(
                TOP,
                TOP_RIGHT,
                RIGHT,
                RIGHT_BOTTOM,
            )
    }
}
