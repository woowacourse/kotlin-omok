package omok.model.rule

import omok.model.board.Board

object ExceedFiveChecker : OmokRule(Board.board) {
    fun checkMoreThanFive(
        x: Int,
        y: Int,
    ): Boolean = directions.map { direction -> checkMoreThanFive(x, y, direction[0], direction[1]) }.contains(true)

    private fun checkMoreThanFive(
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
    ): Boolean {
        val (stone1, blink1) = search(x, y, -dx, -dy)
        val (stone2, blink2) = search(x, y, dx, dy)

        return when {
            blink1 + blink2 == 0 && stone1 + stone2 > 4 -> true
            else -> false
        }
    }
}
