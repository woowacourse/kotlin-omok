class GameBoard {
    private val _stones = mutableListOf<Stone>()
    val stones get() = _stones.toList()
    val rule = Rule()

    fun addStone(stone: Stone): Boolean {
        if (isExistPosition(stone)) return false
        _stones.add(stone)
        return true
    }

    fun lastStone(): Stone? = stones.lastOrNull()

    fun isWin(): Boolean {
        val lastStone = _stones.last()
        when {
            rule.isHorizontalWin(lastStone, stones) -> return true
            rule.isVerticalWin(lastStone, stones) -> return true
            rule.isIncreasingDiagonalWin(lastStone, stones) -> return true
            rule.isDecreasingDiagonalWin(lastStone, stones) -> return true
        }
        return false
    }

    private fun isExistPosition(stone: Stone): Boolean = stones.any { existedStone -> existedStone.isSamePosition(stone) }
}
