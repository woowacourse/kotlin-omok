package woowacourse.omok.model.rule.library

abstract class OmokRule(
    boardSize: Int,
    private val currentStone: Int = BLACK_STONE,
    val opponentStone: Int = WHITE_STONE,
) {
    private val startIndex = 0
    private val endIndex = boardSize - 1
    val edges = listOf(startIndex, endIndex)

    abstract fun abide(
        board: List<List<Int>>,
        position: Pair<Int, Int>,
    ): Boolean

    protected val directions = listOf(1 to 0, 1 to 1, 0 to 1, 1 to -1)

    protected fun search(
        board: List<List<Int>>,
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
            when (board[y][x]) {
                currentStone -> {
                    stone++
                    blink = blinkCount
                }

                opponentStone -> break
                EMPTY_STONE -> if (blink == 1 || blinkCount++ == 1) break
                else -> throw IllegalArgumentException("스톤 케이스를 에러")
            }
        }
        return stone to blink
    }

    protected fun countToWall(
        board: List<List<Int>>,
        position: Pair<Int, Int>,
        direction: Pair<Int, Int>,
    ): Int {
        var (x, y) = position
        val (dx, dy) = direction
        var distance = 0
        while (!willExceedBounds(x, y, dx, dy)) {
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
    ): Boolean {
        return when {
            dx > 0 && x == endIndex -> true
            dx < 0 && x == startIndex -> true
            dy > 0 && y == endIndex -> true
            dy < 0 && y == startIndex -> true
            else -> false
        }
    }

    fun omokRuleData(
        board: List<List<Int>>,
        position: Pair<Int, Int>,
        direction: Pair<Int, Int>,
    ): OmokRuleData {
        val (dx, dy) = direction
        val oppositeDirection = -dx to -dy

        val (stone1, blink1) = search(board, position, oppositeDirection)
        val (stone2, blink2) = search(board, position, direction)

        val leftDown = stone1 + blink1
        val left = dx * (leftDown + 1)
        val down = dy * (leftDown + 1)

        val rightUp = stone2 + blink2
        val right = dx * (rightUp + 1)
        val up = dy * (rightUp + 1)

        return OmokRuleData(stone1, blink1, stone2, blink2, leftDown, left, down, rightUp, right, up)
    }

    companion object {
        protected const val EMPTY_STONE = 0
        const val BLACK_STONE = 1
        const val WHITE_STONE = 2
    }
}

class OmokRuleData(
    val stone1: Int,
    val blink1: Int,
    val stone2: Int,
    val blink2: Int,
    val leftDown: Int,
    val left: Int,
    val down: Int,
    val rightUp: Int,
    val right: Int,
    val up: Int,
)
