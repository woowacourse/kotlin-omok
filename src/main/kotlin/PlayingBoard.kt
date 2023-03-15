class PlayingBoard(placedStones: List<Stone>) : BasedBoard(placedStones.toList()) {
    constructor(vararg stone: Stone) : this(stone.toList())

    override val isFinished: Boolean = false
    override val isWin: Color
        get() {
            throw IllegalStateException("")
        }

    override fun isPossiblePut(point: Point): Boolean =
        !placedStones.any { stone -> stone.point == point }

    override fun putStone(stone: Stone): Board {
        val nextStones = getPlacedStones() + stone
        return when {
            isPossiblePut(stone.point).not() -> throw IllegalArgumentException("")
            checkWin(nextStones, stone.color) -> FinishedBoard(nextStones, stone.color)
            else -> PlayingBoard(nextStones)
        }
    }

    private fun checkWin(placedStones: List<Stone>, color: Color): Boolean {
        val N = 15
        val dx = intArrayOf(1, 1, 0, -1)
        val dy = intArrayOf(0, 1, 1, 1)

        for (i in 1..N) {
            for (j in 1..N) {
                if (placedStones.contains(Stone(Point(i, j), color)).not()) continue

                for (k in 0 until 4) {
                    var cnt = 1
                    var nx = i + dx[k]
                    var ny = j + dy[k]

                    while (nx in 1..N && ny in 1..N && placedStones.contains(Stone(Point(nx, ny), color))) {
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
