package model

object Rule {
    fun checkAddingStone(
        stone: Stone,
        stones: List<Stone>,
    ): AddStoneStatus {
        val countStoneDirections =
            listOf(
                countHorizontalStone(stone, stones),
                countVerticalStone(stone, stones),
                countDecreasingDiagonalStone(stone, stones),
                countIncreasingDiagonalStone(stone, stones),
            )
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
            if (checkFourFoulByAllDirections(stone, addedStones)) isFourFourFlag = true
            if (checkThreeThreeFoulByAllDirections(stone, addedStones)) isThreeThreeFlag = true
        }

        if (isFourFourFlag) return AddStoneStatus.IsFourFour
        if (isThreeThreeFlag) return AddStoneStatus.IsThreeThree
        return AddStoneStatus.IsAble
    }

    private fun countHorizontalStone(
        stone: Stone,
        stones: List<Stone>,
    ): Int =
        directedSearch(Direction.LEFT, stone, stones) +
            directedSearch(
                Direction.RIGHT,
                stone,
                stones,
            ) - DUPLICATED_SELF

    private fun countVerticalStone(
        stone: Stone,
        stones: List<Stone>,
    ): Int =
        directedSearch(Direction.UP, stone, stones) +
            directedSearch(
                Direction.DOWN,
                stone,
                stones,
            ) - DUPLICATED_SELF

    private fun countIncreasingDiagonalStone(
        stone: Stone,
        stones: List<Stone>,
    ): Int =
        directedSearch(Direction.UP_RIGHT, stone, stones) +
            directedSearch(
                Direction.DOWN_LEFT,
                stone,
                stones,
            ) - DUPLICATED_SELF

    private fun countDecreasingDiagonalStone(
        stone: Stone,
        stones: List<Stone>,
    ): Int =
        directedSearch(Direction.DOWN_RIGHT, stone, stones) +
            directedSearch(
                Direction.UP_LEFT,
                stone,
                stones,
            ) - DUPLICATED_SELF

    private fun directedSearch(
        direction: Direction,
        stone: Stone,
        stones: List<Stone>,
    ): Int {
        if (stone.position.isEdgePosition(direction)) return 1
        val expectedNextStone = Stone(direction.nextPosition(stone.position), stone.color)
        val nextStone =
            stones.find { existedStone ->
                existedStone.isSamePosition(
                    expectedNextStone,
                ) &&
                    existedStone.isSameColor(expectedNextStone)
            }
        if (nextStone != null) return directedSearch(direction, nextStone, stones) + 1
        return 1
    }

    private fun checkThreeThreeFoulByAllDirections(
        stone: Stone,
        stones: List<Stone>,
    ): Boolean {
        if (stone.color == StoneColor.WHITE) return false
        val directions =
            listOf(
                Direction.UP,
                Direction.RIGHT,
                Direction.UP_LEFT,
                Direction.DOWN_LEFT,
            )
        var openThreeCount = 0

        for (direction in directions) {
            for (offset in -5..-1) {
                val segmentStart = stone.position.moveOrNull(direction, offset)
                val segmentEnd = segmentStart?.moveOrNull(direction, 6)
                if (segmentStart == null || segmentEnd == null) continue
                if (stones.find { it.position.isSamePosition(segmentStart) }?.color?.isSameColor(stone.color) == false) {
                    continue
                }
                if (stones.find { it.position.isSamePosition(segmentEnd) }?.color?.isSameColor(stone.color) == false) {
                    continue
                }

                if (checkThreeThreeFoul(stone, stones, segmentStart, segmentEnd, direction)) {
                    openThreeCount++
                    break
                }
            }
        }
        return openThreeCount >= 2
    }

    private fun checkThreeThreeFoul(
        stone: Stone,
        stones: List<Stone>,
        startPosition: Position,
        lastPosition: Position,
        direction: Direction,
    ): Boolean {
        var pos = direction.nextPosition(startPosition)
        var stoneCount = 0
        var blankCount = 0
        while (true) {
            val currentStone = stones.find { it.position.isSamePosition(pos) }
            if (currentStone?.color == stone.color) {
                stoneCount++
            } else if (currentStone == null) {
                blankCount++
            } else {
                return false
            }
            pos = direction.nextPosition(pos)
            if (pos.isSamePosition(lastPosition)) break
        }
        return (stoneCount == 3 && blankCount == 2)
    }

    private fun checkFourFoulByAllDirections(
        stone: Stone,
        stones: List<Stone>,
    ): Boolean {
        if (stone.color == StoneColor.WHITE) return false

        val directions =
            listOf(
                Direction.UP,
                Direction.RIGHT,
                Direction.UP_LEFT,
                Direction.DOWN_LEFT,
            )
        val uniqueSegments = mutableSetOf<List<Stone>>()

        for (direction in directions) {
            for (offset in -4..0) {
                val segmentStart = stone.position.moveOrNull(direction, offset) ?: continue
                val segmentEnd = segmentStart.moveOrNull(direction, 5) ?: continue
                val fourStones = checkFourFoul(stone, stones, segmentStart, segmentEnd, direction)
                if (fourStones != null) {
                    uniqueSegments.add(fourStones)
                }
            }
        }
        return uniqueSegments.size >= 2
    }

    private fun checkFourFoul(
        stone: Stone,
        stones: List<Stone>,
        startPosition: Position,
        lastPosition: Position,
        direction: Direction,
    ): List<Stone>? {
        var pos = startPosition
        var blankCount = 0
        var index = 0
        val firstStone = stones.find { it.position.isSamePosition(pos) }
        val sameStones = mutableListOf<Stone>()

        while (true) {
            val currentStone = stones.find { it.position.isSamePosition(pos) }
            if (currentStone?.color == stone.color) {
                sameStones.add(currentStone)
            } else if (currentStone == null) {
                blankCount++
            }
            if (pos.isSamePosition(lastPosition)) break
            pos = direction.nextPosition(pos)
            index++
        }

        val lastStone = stones.find { it.position == lastPosition }
        if (sameStones.size != 4) return null

        if (firstStone?.color != stone.color && lastStone?.color != stone.color) {
            if (!(firstStone != null && lastStone != null)) return sameStones
            return null
        }

        return null
    }

    const val DUPLICATED_SELF = 1
}
