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

    fun isPlaced(stone: Stone): Boolean = stones.contains(stone)

    fun checkWin(startStone: Stone): Boolean {
        val directions = listOf(RIGHT_DIRECTION, TOP_DIRECTION, RIGHT_TOP_DIRECTION, LEFT_BOTTOM_DIRECTION)

        for (moveDirection in directions) {
            val forwardCount = startStone.position.checkStraight(this, moveDirection, FORWARD_WEIGHT)
            val backCount = startStone.position.checkStraight(this, moveDirection, BACK_WEIGHT)
            if (forwardCount + backCount - 1 >= MINIMUM_WIN_CONDITION) return true
        }
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
        private const val BACK_WEIGHT = -1
    }
}
