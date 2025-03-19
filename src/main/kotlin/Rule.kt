class Rule {
    fun isHorizontalWin(
        stone: Stone,
        stones: List<Stone>,
    ): Boolean =
        directedSearch(Direction.LEFT, stone, stones) +
            directedSearch(
                Direction.RIGHT,
                stone,
                stones,
            ) - DUPLICATED_SELF >= 5

    fun isVerticalWin(
        stone: Stone,
        stones: List<Stone>,
    ): Boolean =
        directedSearch(Direction.UP, stone, stones) +
            directedSearch(
                Direction.DOWN,
                stone,
                stones,
            ) - DUPLICATED_SELF >= 5

    fun isIncreasingDiagonalWin(
        stone: Stone,
        stones: List<Stone>,
    ): Boolean =
        directedSearch(Direction.UP_RIGHT, stone, stones) +
            directedSearch(
                Direction.DOWN_LEFT,
                stone,
                stones,
            ) - DUPLICATED_SELF >= 5

    fun isDecreasingDiagonalWin(
        stone: Stone,
        stones: List<Stone>,
    ): Boolean =
        directedSearch(Direction.DOWN_RIGHT, stone, stones) +
            directedSearch(
                Direction.UP_LEFT,
                stone,
                stones,
            ) - DUPLICATED_SELF >= 5

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

    companion object {
        const val DUPLICATED_SELF = 1
    }
}
