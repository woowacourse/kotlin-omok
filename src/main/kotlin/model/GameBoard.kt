package model

import model.judge.OmokJudge

class GameBoard {
    private val _stones = mutableListOf<Stone>()
    val stones get() = _stones.toList()

    fun addStone(stone: Stone?): AddStoneStatus {
        if (stone == null) return AddStoneStatus.IsUnAblePosition
        if (isExistPosition(stone)) return AddStoneStatus.IsExist
        val checkAddingStone = OmokJudge.checkAddingStone(stone, stones)
        if (checkAddingStone != AddStoneStatus.IsAble) return checkAddingStone
        _stones.add(stone)
        return AddStoneStatus.IsAble
    }

    fun lastStone(): Stone? = stones.lastOrNull()

    private fun isExistPosition(stone: Stone): Boolean = stones.any { existedStone -> existedStone.isSamePosition(stone) }

    companion object {
        val COL_RANGE = 1..15
        val ROW_RANGE = 1..15
    }
}
