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

    abstract fun isWin(): Boolean

    fun stones() = stones.stones
}
