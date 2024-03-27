package omok.library

abstract class OmokRule(
    boardSize: Int,
    private val currentStone: Int = BLACK_STONE,
    val opponentStone: Int = WHITE_STONE,
) {
    private val maxX = boardSize - 1
    private val maxY = boardSize - 1
    protected val xEdge = listOf(MIN_X, maxX)
    protected val yEdge = listOf(MIN_Y, maxY)

    abstract fun validate(
        board: Array<Array<Int>>,
        position: Pair<Int, Int>,
    ): Boolean

    protected val directions = listOf(Pair(1, 0), Pair(1, 1), Pair(0, 1), Pair(1, -1))

    protected fun search(
        board: Array<Array<Int>>,
        position: Pair<Int, Int>,
        direction: Pair<Int, Int>,
    ): Pair<Int, Int> {
        var (x, y) = position
        val (dx, dy) = direction
        var stone = 0
        var blink = 0
        var blinkCount = 0
        while (willExceedBounds(x, y, dx, dy).not()) {
            x += dx
            y += dy
            when (board[x][y]) {
                currentStone -> {
                    stone++
                    blink = blinkCount
                }

                opponentStone -> break
                EMPTY_STONE -> {
                    if (blink == 1) break
                    if (blinkCount++ == 1) break
                }

                else -> throw IllegalArgumentException("스톤 케이스를 에러")
            }
        }
        return Pair(stone, blink)
    }

    protected fun countToWall(
        board: Array<Array<Int>>,
        position: Pair<Int, Int>,
        direction: Pair<Int, Int>,
    ): Int {
        var (x, y) = position
        val (dx, dy) = direction
        var distance = 0
        while (willExceedBounds(x, y, dx, dy).not()) {
            x += dx
            y += dy
            when (board[y][x]) {
                in listOf(currentStone, EMPTY_STONE) -> distance++
                opponentStone -> break
                else -> throw IllegalArgumentException()
            }
        }
        return distance
    }

    private fun willExceedBounds(
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
    ): Boolean =
        when {
            dx > 0 && x == maxX -> true
            dx < 0 && x == MIN_X -> true
            dy > 0 && y == maxY -> true
            dy < 0 && y == MIN_Y -> true
            else -> false
        }

    companion object {
        protected const val EMPTY_STONE = 0
        const val BLACK_STONE = 1
        const val WHITE_STONE = 2
        const val MIN_X = 0
        const val MIN_Y = 0
    }
}
