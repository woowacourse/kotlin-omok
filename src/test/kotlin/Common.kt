fun Stone(x: Int, y: Int, color: Color = Color.BLACK) = Stone(Position(x - 1, y - 1), color)
fun BasedBoard(vararg stones: Stone) = object : BasedBoard(stones.toList()) {
    override val isFinished: Boolean
        get() = false

    override val winningColor: Color
        get() {
            throw IllegalStateException("")
        }

    override fun isPossiblePut(point: Position): Boolean {
        return true
    }

    override fun putStone(stone: Stone): Board {
        return this
    }
}
