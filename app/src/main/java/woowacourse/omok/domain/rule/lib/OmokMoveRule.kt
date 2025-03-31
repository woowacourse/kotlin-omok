package woowacourse.omok.domain.rule.lib

abstract class OmokMoveRule(
    val currentStone: Int,
    val isWinningRule: Boolean,
) {
    val opponentStone: Int = if (currentStone == BLACK_STONE) WHITE_STONE else BLACK_STONE

    abstract fun validate(
        board: List<List<Int>>,
        position: Pair<Int, Int>,
    ): Boolean

    protected val directions = listOf(Pair(1, 0), Pair(1, 1), Pair(0, 1), Pair(1, -1))

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
        while (willExceedBounds(board.size, x, y, dx, dy).not()) {
            x += dx
            y += dy
            when (board[y][x]) {
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
        position: Pair<Int, Int>,
        direction: Pair<Int, Int>,
    ): Int {
        var (x, y) = position
        val (dx, dy) = direction
        var distance = 0
        while (willExceedBounds(board.size, x, y, dx, dy).not()) {
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
        boardSize: Int,
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
    ): Boolean =
        when {
            dx > 0 && x == boardSize - 1 -> true
            dx < 0 && x == 0 -> true
            dy > 0 && y == boardSize - 1 -> true
            dy < 0 && y == 0 -> true
            else -> false
        }

    companion object {
        const val EMPTY_STONE = 0
        const val BLACK_STONE = 1
        const val WHITE_STONE = 2
    }
}
