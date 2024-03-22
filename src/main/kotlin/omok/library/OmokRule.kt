package omok.library

class OmokRule(
    private val board: List<List<Int>>,
    private val currentStone: Int = BLACK_STONE,
    private val otherStone: Int = WHITE_STONE,
    private val boardSize: Int,
) {
    private val directions = listOf(listOf(1, 0), listOf(1, 1), listOf(0, 1), listOf(1, -1))

    fun checkThreeThree(
        x: Int,
        y: Int,
    ): Boolean {
        val sum = directions.sumOf { direction -> checkOpenThree(x, y, direction[0], direction[1]) }
        return sum >= 2
    }

    fun countFourFour(
        x: Int,
        y: Int,
    ): Boolean {
        val sum = directions.sumOf { direction -> checkOpenFour(x, y, direction[0], direction[1]) }
        return sum >= 2
    }

    fun checkMoreThanFive(
        x: Int,
        y: Int,
    ): Boolean = directions.map { direction -> checkMoreThanFive(x, y, direction[0], direction[1]) }.contains(true)

    private fun checkOpenThree(
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
    ): Int {
        val (stone1, blink1) = search(x, y, -dx, -dy)
        val (stone2, blink2) = search(x, y, dx, dy)
        val leftDown = stone1 + blink1
        val rightUp = stone2 + blink2

        if (stone1 + stone2 != 2 || blink1 + blink2 == 2) return 0
        if (dx != 0 && (x - leftDown < 0 || x + rightUp >= boardSize)) return 0
        if (dy != 0 && (y - leftDown < 0 || y + rightUp >= boardSize)) return 0
        if (board[y - dy * (leftDown - 1)][x - dx * (leftDown - 1)] == otherStone) return 0
        if (board[y + dy * (rightUp - 1)][x + dx * (rightUp - 1)] == otherStone) return 0

        return 1
    }

    private fun checkOpenFour(
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
    ): Int {
        val (stone1, blink1) = search(x, y, -dx, -dy)
        val (stone2, blink2) = search(x, y, dx, dy)
        val leftDown = stone1 + blink1
        val rightUp = stone2 + blink2

        if (blink1 + blink2 == 2 && (stone1 + stone2 == 4 || stone1 + stone2 == 5)) return 2
        if (stone1 + stone2 != 3 || blink1 + blink2 == 2) return 0
        if (dx != 0 && (x - dx * leftDown < 0 || x + dx * rightUp >= boardSize)) return 0
        if (dy != 0 && (y - dy * leftDown < 0 || y + dy * rightUp >= boardSize)) return 0
        if (board[y - dy * (leftDown - 1)][x - dx * (leftDown - 1)] == otherStone) return 0
        if (board[y + dy * (rightUp - 1)][x + dx * (rightUp - 1)] == otherStone) return 0

        return 1
    }

    private fun checkMoreThanFive(
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
    ): Boolean {
        val (stone1, blink1) = search(x, y, -dx, -dy)
        val (stone2, blink2) = search(x, y, dx, dy)

        return blink1 + blink2 == 0 && stone1 + stone2 > 4
    }

    private fun search(
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
        private const val EMPTY_STONE = 0
        private const val BLACK_STONE = 1
        private const val WHITE_STONE = 2
        private const val MIN_X = 0
    }
}
