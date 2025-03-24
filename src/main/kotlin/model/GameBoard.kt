package model

sealed class AddStoneStatus {
    data object IsExist : AddStoneStatus()

    data object IsThreeThree : AddStoneStatus()

    data object IsFourFour : AddStoneStatus()

    data object IsWin : AddStoneStatus()

    data object IsAble : AddStoneStatus()

    data object IsOverFive : AddStoneStatus()

    data object IsUnAblePosition : AddStoneStatus()
}

class GameBoard {
    private val _stones = mutableListOf<Stone>()
    val stones get() = _stones.toList()

    fun addStone(stone: Stone?): AddStoneStatus {
        if (stone == null) return AddStoneStatus.IsUnAblePosition
        if (isExistPosition(stone)) return AddStoneStatus.IsExist
        val checkAddingStone = Rule.checkAddingStone(stone, stones)
        if (checkAddingStone != AddStoneStatus.IsAble) return checkAddingStone
        _stones.add(stone)
        return AddStoneStatus.IsAble
    }

    fun lastStone(): Stone? = stones.lastOrNull()

    private fun isExistPosition(stone: Stone): Boolean = stones.any { existedStone -> existedStone.isSamePosition(stone) }
}
