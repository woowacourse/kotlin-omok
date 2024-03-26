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
        direction: Pair<Int, Int>,
    ): Int {
        var currentRow = start.row + direction.first
        var currentCol = start.col + direction.second
        var count = 0

        while (currentRow in 0..14 && currentCol in 0..14 &&
            stones.stones.any { it.point.row == currentRow && it.point.col == currentCol }) {
            count++
            currentRow += direction.first
            currentCol += direction.second
        }

        return count
    }

    companion object {
        val directions =
            arrayOf(
                Pair(0, 1),
                Pair(1, 0),
                Pair(1, 1),
                Pair(-1, 1),
            )
    }
}
