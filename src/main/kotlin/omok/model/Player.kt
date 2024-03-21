package omok.model

abstract class Player() {
    protected val stones: Stones = Stones()

    fun getStones(): List<Stone> = stones.stones

    open fun add(stone: Stone) {
        require(!duplicatedPoint(stone)) { "중복된 위치다 이녀석아!" }
        stones.add(stone)
    }

    fun lastStone(): Stone? = stones.lastStone()

    private fun duplicatedPoint(stone: Stone): Boolean {
        return stones.match(stone)
    }

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
}
