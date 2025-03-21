sealed class AddStoneStatus {
    data object IsExist : AddStoneStatus()

    data object IsThreeThree : AddStoneStatus()

    data object IsFourFour : AddStoneStatus()

    data object IsWin : AddStoneStatus()

    data object IsAble : AddStoneStatus()
}

class GameBoard {
    private val _stones = mutableListOf<Stone>()
    val stones get() = _stones.toList()
    val rule = Rule()

    fun addStone(stone: Stone): AddStoneStatus {
        if (isExistPosition(stone)) return AddStoneStatus.IsExist
        _stones.add(stone)
        if (isWin()) return AddStoneStatus.IsWin
        _stones.remove(stone)
        when {
            rule.checkThreeThreeFoulByAllDirections(stone, stones) -> return AddStoneStatus.IsThreeThree
            rule.checkFourFoulByAllDirections(stone, stones) -> return AddStoneStatus.IsFourFour
        }
        _stones.add(stone)
        return AddStoneStatus.IsAble
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
