package woowacourse.omok.domain.rule

sealed class OmokRule(
    val boardSize: Int,
    val currentStone: Int = BLACK_STONE,
    val otherStone: Int = WHITE_STONE,
) {
    protected val directions = listOf(listOf(1, 0), listOf(1, 1), listOf(0, 1), listOf(1, -1))

    abstract fun isValidPosition(
        board: List<List<Int>>,
        x: Int,
        y: Int,
    ): Boolean

    fun search(
        board: List<List<Int>>,
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
    ): Pair<Int, Int> {
        var toRight = x
        var toTop = y
        var stone = 0
        var blink = 0
        var blinkCount = 0
        while (true) {
            if (dx > 0 && toRight == boardSize - 1) break
            if (dx < 0 && toRight == MIN_X) break
            if (dy > 0 && toTop == boardSize - 1) break
            if (dy < 0 && toTop == MIN_X) break
            toRight += dx
            toTop += dy
            when (board[toTop][toRight]) {
                currentStone -> {
                    stone++
                    blink = blinkCount
                }

                otherStone -> break
                EMPTY_STONE -> {
                    if (blink == 1) break
                    if (blinkCount++ == 1) break
                }

                else -> throw IllegalArgumentException()
            }
        }
        return Pair(stone, blink)
    }

    companion object {
        const val EMPTY_STONE = 0
        const val BLACK_STONE = 1
        const val WHITE_STONE = 2
        const val MIN_X = 0
        const val MIN_Y = 0
    }
}
