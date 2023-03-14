data class Stones(private val stones: List<Stone>) {
    constructor(vararg stones: Stone) : this(stones.toList())

    init {
        require(stones.distinct().size == stones.size) { DUPLICATED_ERROR_MESSAGE }
    }

    fun add(newStone: Stone): Stones = Stones(stones + newStone)

    fun hasStone(stone: Stone): Boolean = stones.contains(stone)

    fun checkWin(startStone: Stone): Boolean {
        val directions = listOf(RIGHT_DIRECTION, TOP_DIRECTION, RIGHT_TOP_DIRECTION, LEFT_BOTTOM_DIRECTION)

        for (moveDirection in directions) {
            if (checkStraight(startStone.position, moveDirection, FORWARD_WEIGHT)) return true
            if (checkStraight(startStone.position, moveDirection, BACK_WEIGHT)) return true
        }
        return false
    }

    private fun checkStraight(
        startPosition: Position,
        direction: Pair<Int, Int>,
        weight: Int = FORWARD_WEIGHT
    ): Boolean {
        val (startX, startY) = Pair(startPosition.x, startPosition.y)
        var count = 1
        var (currentX, currentY) = Pair(startX + direction.first * weight, startY + direction.second * weight)
        while (currentX in Position.POSITION_RANGE && currentY in Position.POSITION_RANGE && hasStone(Stone.of(currentX, currentY))) {
            count++
            currentX += direction.first * weight
            currentY += direction.second * weight
        }
        if (count >= MINIMUM_WIN_CONDITION) return true
        return false
    }

    companion object {
        private const val DUPLICATED_ERROR_MESSAGE = "중복되는 위치의 오목알을 가질 수 없습니다."
        private const val MINIMUM_WIN_CONDITION = 5

        private val RIGHT_DIRECTION = Pair(1, 0)
        private val TOP_DIRECTION = Pair(0, 1)
        private val RIGHT_TOP_DIRECTION = Pair(1, 1)
        private val LEFT_BOTTOM_DIRECTION = Pair(-1, -1)

        private const val FORWARD_WEIGHT = 1
        private const val BACK_WEIGHT = 1
    }
}
