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
        direction: Point,
    ): Int {
        var currentPoint = Point(start.row + direction.row, start.col + direction.col)
        var count = 0

        while (currentPoint.row in 0..14 &&
            currentPoint.col in 0..14 &&
            stones.stones.any { it.point == currentPoint }
        ) {
            count++
            currentPoint = Point(currentPoint.row + direction.row, currentPoint.col + direction.col)
        }

        return count
    }

    companion object {
        private const val ERROR_DUPLICATED_POSITION = "중복된 위치입니다."
    }
}
