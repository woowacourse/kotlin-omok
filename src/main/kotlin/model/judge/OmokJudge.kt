package model.judge

import model.AddStoneStatus
import model.Direction
import model.Stone
import model.StoneColor

object OmokJudge {
    fun checkAddingStone(
        stone: Stone,
        stones: List<Stone>,
    ): AddStoneStatus {
        val directions = listOf(Direction.UP, Direction.LEFT, Direction.DOWN_LEFT, Direction.UP_LEFT)

        val countStoneDirections = directions.map { direction -> countStone(stone, stones, direction) }

        if (countStoneDirections.any { it == 5 }) return AddStoneStatus.IsWin
        if (countStoneDirections.any { it > 5 } && stone.color == StoneColor.BLACK) return AddStoneStatus.IsOverFive

        return checkFoul(stones, stone)
    }

    private fun checkFoul(
        stones: List<Stone>,
        addedStone: Stone,
    ): AddStoneStatus {
        val addedStones = stones + addedStone
        var isFourFourFlag = false
        var isThreeThreeFlag = false
        addedStones.forEach { stone ->
            if (ThreeThreeCheck.checkFoulByAllDirections(stone, addedStones)) isThreeThreeFlag = true
            if (FourFourCheck.checkFoulByAllDirections(stone, addedStones)) isFourFourFlag = true
        }

        if (isFourFourFlag) return AddStoneStatus.IsFourFour
        if (isThreeThreeFlag) return AddStoneStatus.IsThreeThree
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

    const val DUPLICATED_SELF = 1
}
