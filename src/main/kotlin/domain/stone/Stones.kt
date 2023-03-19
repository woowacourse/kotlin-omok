package domain.stone

import domain.position.Position

data class Stones(private val stones: List<Stone>) {
    val lastStone: Stone
        get() = stones.last().copy()

    constructor(vararg stones: Stone) : this(stones.toList())

    init {
        require(stones.distinct().size == stones.size) { DUPLICATED_ERROR_MESSAGE }
    }

    fun getPositions(): List<Position> = stones.map { it.position.copy() }

    fun add(newStone: Stone): Stones = Stones(stones + newStone)

    fun hasStone(stone: Stone): Boolean = stones.contains(stone)

    fun checkWin(startStone: Stone): Boolean {
        val directions = listOf(RIGHT_DIRECTION, TOP_DIRECTION, RIGHT_TOP_DIRECTION, LEFT_BOTTOM_DIRECTION)

        for (moveDirection in directions) {
            val forwardCount = checkStraight(startStone.position, moveDirection, FORWARD_WEIGHT)
            val backCount = checkStraight(startStone.position, moveDirection, BACK_WEIGHT)
            if (forwardCount + backCount - 1 >= MINIMUM_WIN_CONDITION) return true
        }
        return false
    }

    private fun checkStraight(
        startPosition: Position,
        direction: Pair<Int, Int>,
        weight: Int = FORWARD_WEIGHT,
    ): Int {
        val (startX, startY) = Pair(startPosition.row, startPosition.col)
        var count = 1
        var (currentX, currentY) = Pair(startX + direction.first * weight, startY + direction.second * weight)
        while (inRange(currentX, currentY) && hasStone(Stone.of(currentX, currentY))) {
            count++
            currentX += direction.first * weight
            currentY += direction.second * weight
        }
        return count
    }

    private fun inRange(x: Int, y: Int) = x in Position.POSITION_RANGE && y in Position.POSITION_RANGE

    companion object {
        private const val DUPLICATED_ERROR_MESSAGE = "중복되는 위치의 오목알을 가질 수 없습니다."
        private const val MINIMUM_WIN_CONDITION = 5

        private val RIGHT_DIRECTION = Pair(1, 0)
        private val TOP_DIRECTION = Pair(0, 1)
        private val RIGHT_TOP_DIRECTION = Pair(1, 1)
        private val LEFT_BOTTOM_DIRECTION = Pair(-1, -1)

        private const val FORWARD_WEIGHT = 1
        private const val BACK_WEIGHT = -1
    }
}
