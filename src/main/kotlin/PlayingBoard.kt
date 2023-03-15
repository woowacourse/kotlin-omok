class PlayingBoard(private val stones: List<Stone>) : Board {
    constructor(vararg stone: Stone) : this(stone.toList())

    override val isFinished: Boolean = false
    override val isWin: Color
        get() {
            throw IllegalStateException("")
        }

    override fun isPossiblePut(point: Point): Boolean =
        !stones.any { stone -> stone.point == point }

    override fun getLatestPoint(color: Color): Point? {
        return stones.findLast { it.color == color }?.point
    }

    override fun getStones(): List<Stone> {
        return stones.toList()
    }

    override fun putStone(stone: Stone): Board {
        return this
    }
}
