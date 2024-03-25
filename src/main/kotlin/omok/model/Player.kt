package omok.model

abstract class Player() {
    protected val stones: Stones = Stones()

    fun getStones(): List<Stone> = stones.stones

    open fun add(stone: Stone) {
        require(!duplicatedPoint(stone)) { ERROR_DUPLICATED_POSITION }
        stones.add(stone)
    }

    fun lastStone(): Stone? = stones.lastStone()

    private fun duplicatedPoint(stone: Stone): Boolean = stones.match(stone)

    open fun checkContinuity(stone: Stone): Boolean = true

    fun countStones(
        start: Point,
        direction: List<Int>,
    ): Int {
        var currentPoint = Pair(start.row + direction[0], start.col + direction[1])
        var count = 0

        while (currentPoint.first in 0..14 &&
            currentPoint.second in 0..14 &&
            stones.stones.any { it.point == Point(currentPoint.first, currentPoint.second) }
        ) {
            count++
            currentPoint = Pair(currentPoint.first + direction[0], currentPoint.second + direction[1])
        }

        return count
    }

    companion object {
        private const val ERROR_DUPLICATED_POSITION = "이미 수가 놓여진 곳에는 수를 놓으실 수 없습니다. 다른 위치에 수를 놓아주세요."
    }
}
