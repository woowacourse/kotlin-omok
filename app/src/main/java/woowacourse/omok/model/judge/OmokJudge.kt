package model.judge

import woowacourse.omok.model.AddStoneStatus
import woowacourse.omok.model.Direction
import woowacourse.omok.model.Stone
import woowacourse.omok.model.StoneColor

object OmokJudge {
    fun checkAddingStone(
        stone: Stone,
        stones: List<Stone>,
    ): AddStoneStatus {
        val directions = listOf(Direction.UP, Direction.LEFT, Direction.DOWN_LEFT, Direction.UP_LEFT)

        val countStoneDirections = directions.map { direction -> countStone(stone, stones, direction) }

        if (countStoneDirections.any { it == REQUIRE_WIN_STONE_COUNT }) return AddStoneStatus.IsWin
        if (countStoneDirections.any { it > REQUIRE_WIN_STONE_COUNT } && stone.color == StoneColor.BLACK) return AddStoneStatus.Failed.IsOverFive

        return checkFoul(stones, stone)
    }

    private fun checkFoul(
        stones: List<Stone>,
        addedStone: Stone,
    ): AddStoneStatus {
        val addedStones = stones + addedStone
        var isFourFourFlag = false
        var isThreeThreeFlag = false
        if (ThreeThreeCheck.checkFoulByAllDirections(addedStone, addedStones)) isThreeThreeFlag = true
        if (FourFourCheck.checkFoulByAllDirections(addedStone, addedStones)) isFourFourFlag = true
        if (isFourFourFlag) return AddStoneStatus.Failed.IsFourFour
        if (isThreeThreeFlag) return AddStoneStatus.Failed.IsThreeThree
        return AddStoneStatus.IsAble
    }

    private fun countStone(
        stone: Stone,
        stones: List<Stone>,
        direction: Direction,
    ): Int = directedSearch(direction, stone, stones) + directedSearch(direction.opposite(), stone, stones) - DUPLICATED_SELF

    private fun directedSearch(
        direction: Direction,
        stone: Stone,
        stones: List<Stone>,
    ): Int {
        if (stone.position.isEdgePosition(direction)) return 1
        val expectedNextStone = Stone(direction.nextPosition(stone.position), stone.color)
        val nextStone =
            stones.find { existedStone ->
                existedStone.position ==
                    expectedNextStone.position &&
                    existedStone.color == expectedNextStone.color
            }
        if (nextStone != null) return directedSearch(direction, nextStone, stones) + 1
        return 1
    }

    private const val DUPLICATED_SELF = 1
    private const val REQUIRE_WIN_STONE_COUNT = 5
}
