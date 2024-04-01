package omok.model

class Stones {
    private val _stones = mutableListOf<Stone>()
    val stones: List<Stone>
        get() = _stones.toList()

    fun add(stone: Stone): Boolean {
        if (!occupied(stone.point)) {
            _stones.add(stone)
            return true
        }
        return false
    }

    fun occupied(point: Point): Boolean {
        return stones.any { stone ->
            stone.point == point
        }
    }

    fun match(stone: Stone): Boolean {
        return (stones.contains(stone))
    }

    fun lastStone(): Stone? = stones.lastOrNull()
}
