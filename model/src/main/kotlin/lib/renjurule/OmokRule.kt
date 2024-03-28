package lib.renjurule

abstract class OmokRule {
    val directions = listOf(listOf(1, 0), listOf(1, 1), listOf(0, 1), listOf(1, -1))

    fun search(
        board: Array<Array<Int>>,
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
    ): Pair<Int, Int> {
        // 반대의 반대의 반대,,
        var toRight = x
        var toTop = y
        var stone = DEFAULT_COUNT
        var blink = DEFAULT_COUNT
        var blinkCount = DEFAULT_COUNT
        while (true) {
            if (dx > DIRECTION_STANDARD && toRight == BOARD_SIZE - 1) break
            if (dx < DIRECTION_STANDARD && toRight == MIN_X) break
            if (dy > DIRECTION_STANDARD && toTop == BOARD_SIZE - 1) break
            if (dy < DIRECTION_STANDARD && toTop == MIN_X) break
            toRight += dx
            toTop += dy
            when (board[toTop][toRight]) {
                CURRENT_STONETYPE -> {
                    stone++
                    blink = blinkCount
                }

                OTHER_STONETYPE -> break
                EMPTY_STONETYPE -> {
                    if (blink == BLANK_ALLOWANCE) break
                    if (blinkCount++ == BLANK_ALLOWANCE) break
                }

                else -> throw IllegalArgumentException()
            }
        }
        return Pair(stone, blink)
    }

    companion object {
        const val BOARD_SIZE = 15
        const val EMPTY_STONETYPE = 0
        const val CURRENT_STONETYPE = 1
        const val OTHER_STONETYPE = 2
        const val MIN_X = 0
        const val MIN_Y = 0
        const val DEFAULT_COUNT = 0
        const val DIRECTION_STANDARD = 0
        const val BLANK_ALLOWANCE = 1
        const val COORDINATION_MOVE_OFFSET = 1
        const val INVALID_BLANK_ALLOWANCE = 1
    }
}
