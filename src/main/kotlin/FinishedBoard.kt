class FinishedBoard(private val stones: List<Stone>, override val isWin: Color) : Board {
    override val isFinished: Boolean = true

    override fun isPossiblePut(point: Point): Boolean = false

    // TODO: 고려해보자
    override fun getLatestPoint(color: Color): Point? {

        return stones.findLast { stone ->
            stone.color == color
        }?.point
    }

    override fun getStones(): List<Stone> = stones.toList()

    override fun putStone(stone: Stone): Board = this
}
