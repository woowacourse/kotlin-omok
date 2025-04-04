package woowacourse.omok.model

import model.judge.OmokJudge

class GameBoard {
    private val _stones = mutableListOf<Stone>()
    val stones get() = _stones.toList()

    fun addStone(stone: Stone?): AddStoneStatus {
        if (stone == null) return AddStoneStatus.Failed.IsUnAblePosition
        if (isExistPosition(stone)) return AddStoneStatus.Failed.IsExist
        val checkAddingStone = OmokJudge.checkAddingStone(stone, stones)
        if (checkAddingStone != AddStoneStatus.IsAble) return checkAddingStone
        _stones.add(stone)
        return AddStoneStatus.IsAble
    }

    fun lastStone(): Stone? = stones.lastOrNull()

    fun changeRowRangeSize(
        start: Int,
        end: Int,
    ) {
        rowRange = start..end
    }

    fun changeColRangeSize(
        start: Char,
        end: Char,
    ) {
        colRange = start.code - Col.ASCII_A_OFFSET..end.code - Col.ASCII_A_OFFSET
    }

    private fun isExistPosition(stone: Stone): Boolean = stones.any { existedStone -> existedStone.position == stone.position }

    companion object {
        var colRange = 1..15
            private set
        var rowRange = 1..15
            private set
    }
}
