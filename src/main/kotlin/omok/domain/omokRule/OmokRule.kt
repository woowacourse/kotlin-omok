package omok.domain.omokRule

abstract class OmokRule(
    private val currentStone: Int = BLACK_STONE,
    private val opponentStone: Int = WHITE_STONE,
) {
    protected val directions = listOf(Pair(1, 0), Pair(1, 1), Pair(0, 1), Pair(1, -1))

    abstract fun validate(board: List<List<Int>>, point: Pair<Int, Int>): Boolean

    protected fun search(
        board: List<List<Int>>,
        position: Pair<Int, Int>,
        direction: Pair<Int, Int>,
    ): Pair<Int, Int> {
        var (toRight, toTop) = position
        val (dx, dy) = direction
        var stone = 0
        var blink = 0
        var blinkCount = 0
        while (true) {
            if (dx > 0 && toRight == MAX_X) break
            if (dx < 0 && toRight == MIN_X) break
            if (dy > 0 && toTop == MAX_Y) break
            if (dy < 0 && toTop == MIN_X) break
            toRight += dx
            toTop += dy
            when (board[toTop][toRight]) {
                currentStone -> {
                    stone++
                    blink = blinkCount
                }
                opponentStone -> break
                EMPTY_STONE -> {
                    if (blink == 1) break
                    if (blinkCount++ == 1) break
                }
                else -> throw IllegalArgumentException()
            }
        }
        return Pair(stone, blink)
    }

    protected fun countToWall(
        board: List<List<Int>>,
        position: Pair<Int, Int>,
        direction: Pair<Int, Int>,
    ): Int {
        var (toRight, toTop) = position
        val (dx, dy) = direction
        var distance = 0
        while (true) {
            if (dx > 0 && toRight == MAX_X) break
            if (dx < 0 && toRight == MIN_X) break
            if (dy > 0 && toTop == MAX_Y) break
            if (dy < 0 && toTop == MIN_X) break
            toRight += dx
            toTop += dy
            when (board[toTop][toRight]) {
                in listOf(BLACK_STONE, EMPTY_STONE) -> distance++
                WHITE_STONE -> break
                else -> throw IllegalArgumentException()
            }
        }
        return distance
    }

    companion object {
        protected const val EMPTY_STONE = 0
        const val BLACK_STONE = 1
        const val WHITE_STONE = 2
        protected const val MIN_X = 0
        protected const val MAX_X = 14
        protected const val MIN_Y = 0
        protected const val MAX_Y = 14

        @JvmStatic
        protected val X_Edge = listOf(MIN_X, MAX_X)

        @JvmStatic
        protected val Y_Edge = listOf(MIN_Y, MAX_Y)
    }
}
