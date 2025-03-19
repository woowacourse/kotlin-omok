class GameBoard {
    private val _stones = mutableListOf<Stone>()
    val stones get() = _stones.toList()

    fun addStone(stone: Stone): Boolean {
        if (isExistPosition(stone)) return false
        _stones.add(stone)
        return true
    }

    fun lastStone(): Stone? {
        return stones.lastOrNull()
    }

    private fun isExistPosition(stone: Stone): Boolean {
        return stones.any { existedStone -> existedStone.isSamePosition(stone) }
    }
}
