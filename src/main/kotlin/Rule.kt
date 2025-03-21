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

    fun checkThreeThreeFoulByAllDirections(
        stone: Stone,
        stones: List<Stone>,
    ): Boolean {
        val stoneAddedStones = stones + stone
        if (stone.color == StoneColor.WHITE) return false
        val directions =
            listOf(
                Direction.UP,
                Direction.RIGHT,
                Direction.UP_LEFT,
                Direction.DOWN_RIGHT,
            )
        var openThreeCount = 0

        for (direction in directions) {
            for (offset in -4..0) {
                val segmentStart = stone.position.moveOrNull(direction, offset)
                val segmentEnd = segmentStart?.moveOrNull(direction, 4)

                if (segmentStart == null || segmentEnd == null) continue

                if (checkThreeThreeFoul(stone, stoneAddedStones, segmentStart, segmentEnd, direction)) {
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
        var pos = startPosition
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
            if (pos.isSamePosition(lastPosition)) break
            pos = direction.nextPosition(pos)
        }
        println("$stoneCount $blankCount ${direction.name}")
        return (stoneCount == 3 && blankCount >= 2)
    }

    fun checkFourFoulByAllDirections(
        stone: Stone,
        stones: List<Stone>,
    ): Boolean {
        if (stone.color == StoneColor.WHITE) return false
        val stoneAddedStones = stones + stone

        val directions =
            listOf(
                Direction.UP,
                Direction.RIGHT,
                Direction.UP_LEFT,
                Direction.DOWN_RIGHT,
            )
        var fourCount = 0

        for (direction in directions) {
            for (offset in -4..0) {
                val segmentStart = stone.position.moveOrNull(direction, offset)
                val segmentEnd = segmentStart?.moveOrNull(direction, 5)
                if (segmentStart == null || segmentEnd == null) continue

                if (checkFourFoul(stone, stoneAddedStones, segmentStart, segmentEnd, direction)) {
                    fourCount++
                }
            }
        }
        return fourCount >= 2
    }

    private fun checkFourFoul(
        stone: Stone,
        stones: List<Stone>,
        startPosition: Position,
        lastPosition: Position,
        direction: Direction,
    ): Boolean {
        var pos = startPosition
        var stoneCount = 0
        var blankCount = 0
        var index = 0
        var firstIsBlank = false

        while (true) {
            val currentStone = stones.find { it.position.isSamePosition(pos) }
            if (currentStone?.color == stone.color) {
                stoneCount++
            } else if (currentStone == null) {
                blankCount++
                if (index == 0) firstIsBlank = true
            } else {
                return false
            }
            if (pos.isSamePosition(lastPosition)) break
            pos = direction.nextPosition(pos)
            index++
        }
        val lastIsBlank = stones.find { it.position == lastPosition } == null

        if (stoneCount != 4) return false

        if (firstIsBlank || lastIsBlank) return true

        return false
    }

    private operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> =
        Pair(this.first + other.first, this.second + other.second)

    companion object {
        const val DUPLICATED_SELF = 1
    }
}
