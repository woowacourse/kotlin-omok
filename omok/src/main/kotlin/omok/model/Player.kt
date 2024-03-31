package omok.model

abstract class Player {
    abstract val color: Color
    protected abstract val stones: Stones

    fun add(point: Point) {
        stones.add(Stone(point, color))
    }

    abstract fun isWin(): Boolean

    fun stones() = stones.stones

    fun requireLastStone() = stones.lastStone() ?: throw IllegalStateException("놓여진 바둑이 없습니다.")
}
