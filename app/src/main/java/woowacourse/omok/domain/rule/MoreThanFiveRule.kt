package woowacourse.omok.domain.rule

class MoreThanFiveRule(boardSize: Int) : OmokRule(boardSize) {
    override fun isValidPosition(
        board: List<List<Int>>,
        x: Int,
        y: Int,
    ): Boolean = !checkMoreThanFive(board, x, y)

    private fun checkMoreThanFive(
        board: List<List<Int>>,
        x: Int,
        y: Int,
    ): Boolean =
        directions.map { direction -> checkMoreThanFive(board, x, y, direction[0], direction[1]) }
            .contains(true)

    private fun checkMoreThanFive(
        board: List<List<Int>>,
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
    ): Boolean {
        val (stone1, blink1) = search(board, x, y, -dx, -dy)
        val (stone2, blink2) = search(board, x, y, dx, dy)

        return when {
            blink1 + blink2 == 0 && stone1 + stone2 > 4 -> true
            else -> false
        }
    }
}
