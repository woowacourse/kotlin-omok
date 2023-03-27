package omok.domain.omokRule

import omok.configure.Constants.X_MAX_RANGE
import omok.configure.Constants.X_MIN_RANGE
import omok.configure.Constants.Y_MAX_RANGE
import omok.configure.Constants.Y_MIN_RANGE

abstract class OmokRule(
    private val currentStone: Int = BLACK_STONE,
    val opponentStone: Int = WHITE_STONE,
) {
    abstract fun validate(board: List<List<Int>>, position: RulePosition): Boolean

    protected fun search(
        board: List<List<Int>>,
        position: RulePosition,
        direction: RuleDirection,
    ): Pair<Int, Int> {
        var curPosition = position
        var stone = 0
        var blink = 0
        var blinkCount = 0
        while (willExceedBounds(curPosition, direction).not()) {
            curPosition += direction
            when (board[curPosition.y][curPosition.x]) {
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
        board: List<List<Int>>,
        position: RulePosition,
        direction: RuleDirection,
    ): Int {
        var curPosition = position
        var distance = 0
        while (willExceedBounds(curPosition, direction).not()) {
            curPosition += direction
            when (board[curPosition.y][curPosition.x]) {
                in listOf(currentStone, EMPTY_STONE) -> distance++
                opponentStone -> break
                else -> throw IllegalArgumentException()
            }
        }
        return distance
    }

    protected fun countLine(
        board: List<List<Int>>,
        position: RulePosition,
        direction: RuleDirection,
    ): Int {
        var curPosition = position
        var distance = 0
        while (willExceedBounds(curPosition, direction).not()) {
            curPosition += direction
            when (board[curPosition.y][curPosition.x]) {
                currentStone -> distance++
                opponentStone -> break
                EMPTY_STONE -> break
                else -> throw IllegalArgumentException()
            }
        }
        return distance
    }

    private fun willExceedBounds(position: RulePosition, direction: RuleDirection): Boolean = when {
        direction.x > 0 && position.x == MAX_X -> true
        direction.x < 0 && position.x == MIN_X -> true
        direction.y > 0 && position.y == MAX_Y -> true
        direction.y < 0 && position.y == MIN_Y -> true
        else -> false
    }

    companion object {
        protected const val EMPTY_STONE = 0
        const val BLACK_STONE = 1
        const val WHITE_STONE = 2
        const val MIN_X = 0
        const val MAX_X = X_MAX_RANGE - X_MIN_RANGE
        const val MIN_Y = 0
        const val MAX_Y = Y_MAX_RANGE - Y_MIN_RANGE

        @JvmStatic
        protected val X_Edge = listOf(MIN_X, MAX_X)

        @JvmStatic
        protected val Y_Edge = listOf(MIN_Y, MAX_Y)
    }
}
