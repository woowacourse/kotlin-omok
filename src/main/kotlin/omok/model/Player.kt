package omok.model

abstract class Player {
    abstract val color: Color
    abstract val board: Board
    protected val stones: Stones = Stones()

    open fun add(point: Point) {
        val stone = Stone(point, color)
        board.checkDuplicate(stone)

        stones.add(Stone(point, color))
    }

    fun lastStone(): Stone? = stones.lastStone()

    abstract fun checkContinuity(): Boolean

    fun stones() = stones.stones

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
        val directions =
            arrayOf(
                Point(0, 1),
                Point(1, 0),
                Point(1, 1),
                Point(-1, 1),
            )
    }
}
