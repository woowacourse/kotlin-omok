package woowacourse.omok.domain

class Stones {
    private val _stones = mutableListOf<Stone>()
    val stones: List<Stone>
        get() = _stones.toList()

    fun add(stone: Stone): Boolean {
        if (!isPlaced(stone.position)) {
            _stones.add(stone)
            return true
        }
        return false
    }

    fun reset() = _stones.clear()

    private fun isPlaced(position: Position): Boolean {
        return stones.any { stone ->
            stone.position == position
        }
    }

    fun lastStone(): Stone? = stones.lastOrNull()
}
