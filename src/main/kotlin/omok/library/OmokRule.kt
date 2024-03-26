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

    protected val directions = listOf(Pair(RIGHT, SAME_POSITION), Pair(RIGHT, DOWN), Pair(SAME_POSITION, DOWN), Pair(RIGHT, UP))

    abstract fun validate(
        board: List<List<Int>>,
        position: Pair<Int, Int>,
    ): Boolean

    protected fun search(
        board: List<List<Int>>,
        position: Pair<Int, Int>,
        direction: Pair<Int, Int>,
    ): Pair<Int, Int> {
        var (x, y) = position
        val (dx, dy) = direction
        var stone = INIT_COUNT
        var blink = INIT_COUNT
        var blinkCount = INIT_COUNT
        var isDone = false
        while (willExceedBounds(x, y, dx, dy).not() && !isDone) {
            x += dx
            y += dy
            val triple = result(board, y, x, stone, blink, blinkCount, isDone)
            blink = triple.first
            isDone = triple.second
            stone = triple.third
        }
        return Pair(stone, blink)
    }

    private fun result(
        board: List<List<Int>>,
        y: Int,
        x: Int,
        stone: Int,
        blink: Int,
        blinkCount: Int,
        isDone: Boolean,
    ): Triple<Int, Boolean, Int> {
        var stone1 = stone
        var blink1 = blink
        var blinkCount1 = blinkCount
        var isDone1 = isDone
        when (board[y][x]) {
            currentStone -> {
                stone1++
                blink1 = blinkCount1
            }

            opponentStone -> isDone1 = true
            EMPTY_STONE -> {
                if (blink1 == BLANK_ALLOWANCE) isDone1 = true
                if (blinkCount1++ == BLANK_ALLOWANCE) isDone1 = true
            }

            else -> throw IllegalArgumentException("스톤 케이스를 에러")
        }
        return Triple(blink1, isDone1, stone1)
    }

    protected fun countToWall(
        board: List<List<Int>>,
        position: Pair<Int, Int>,
        direction: Pair<Int, Int>,
    ): Int {
        var (x, y) = position
        val (dx, dy) = direction
        var distance = INIT_COUNT
        distance = getDistance(x, y, dx, dy, board, distance)
        return distance
    }

    private fun getDistance(
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
        board: List<List<Int>>,
        distance: Int
    ): Int {
        var x1 = x
        var y1 = y
        var distance1 = distance
        while (willExceedBounds(x1, y1, dx, dy).not()) {
            x1 += dx
            y1 += dy
            when (board[y1][x1]) {
                in listOf(currentStone, EMPTY_STONE) -> distance1++
                opponentStone -> break
                else -> throw IllegalArgumentException()
            }
        }
        return distance1
    }

    private fun willExceedBounds(
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
    ): Boolean =
        when {
            dx > BOUND_CONDITION && x == maxX -> true
            dx < BOUND_CONDITION && x == MIN_X -> true
            dy > BOUND_CONDITION && y == maxY -> true
            dy < BOUND_CONDITION && y == MIN_Y -> true
            else -> false
        }

    companion object {
        protected const val EMPTY_STONE = 0
        const val BLACK_STONE = 1
        const val WHITE_STONE = 2
        const val MIN_X = 0
        const val MIN_Y = 0
        const val INIT_COUNT = 0
        const val BLANK_ALLOWANCE = 1
        const val BOUND_CONDITION = 0
        const val RIGHT = 1
        const val DOWN = 1
        const val UP = -1
        const val SAME_POSITION = 0
    }
}
