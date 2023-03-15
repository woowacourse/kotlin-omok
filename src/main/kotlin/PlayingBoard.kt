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
        val nextStones = getStones() + stone
        return when {
            isPossiblePut(stone.point).not() -> throw IllegalArgumentException("")
            checkWin(nextStones, stone.color) -> FinishedBoard(stones, stone.color)
            else -> PlayingBoard(nextStones)
        }
    }

    private fun checkWin(stones: List<Stone>, color: Color): Boolean {
        val N = 15
        val dx = intArrayOf(1, 1, 0, -1)
        val dy = intArrayOf(0, 1, 1, 1)

        for (i in 1..N) {
            for (j in 1..N) {
                if (stones.contains(Stone(Point(i, j), color)).not()) continue

                for (k in 0 until 4) {
                    var cnt = 1
                    var nx = i + dx[k]
                    var ny = j + dy[k]

                    while (nx in 1..N && ny in 1..N && stones.contains(Stone(Point(nx, ny), color))) {
                        cnt++
                        nx += dx[k]
                        ny += dy[k]

                        if (cnt >= 5) return true
                    }
                }
            }
        }
        return false
    }
}
