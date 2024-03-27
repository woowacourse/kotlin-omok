package omok.model

abstract class Player {
    abstract val color: Color
    protected abstract val board: Board

    fun add(point: Point) {
        val stone = Stone(point, color)
        board.add(stone)
    }

    abstract fun isWin(): Boolean

    fun stones() = board.stones

    fun requireLastStone() = board.lastStone()?.point ?: throw IllegalStateException("놓여진 바둑이 없습니다.")
}
