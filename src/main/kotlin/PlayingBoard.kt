class PlayingBoard(private val stones: List<Stone>) : Board {
    override val isFinished: Boolean = false
    override val isWin: Color
        get() {
            throw IllegalStateException("")
        }

    override fun isPossiblePut(point: Point): Boolean =
        !stones.any { stone -> stone.point == point }

    override fun getLatestPoint(color: Color): Point? {
        return null
    }

    override fun getStones(): List<Stone> {
        return stones
    }

    override fun putStone(stone: Stone): Board {
        return this
    }
}
