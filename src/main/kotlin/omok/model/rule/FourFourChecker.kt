package omok.model.rule

import omok.model.board.Board

object FourFourChecker : OmokRule() {
    fun checkFourFour(
        x: Int,
        y: Int,
    ): Boolean =
        directions.sumOf { direction ->
            checkOpenFour(x, y, direction.relativeRowDirection, direction.relativeColumnDirection)
        } >= 2

    private fun checkOpenFour(
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
    ): Int {
        val (stone1, blink1) = search(x, y, -dx, -dy)
        val (stone2, blink2) = search(x, y, dx, dy)

        val leftDown = stone1 + blink1
        val left = dx * (leftDown + 1)
        val down = dy * (leftDown + 1)

        val rightUp = stone2 + blink2
        val right = dx * (rightUp + 1)
        val up = dy * (rightUp + 1)

        when {
            blink1 + blink2 == 2 && stone1 + stone2 == 4 -> return 2
            blink1 + blink2 == 2 && stone1 + stone2 == 5 -> return 2
            stone1 + stone2 != 3 -> return 0
            blink1 + blink2 == 2 -> return 0
        }

        val leftDownValid =
            when {
                dx != 0 && Board.isBoardEdge(x - dx * leftDown) -> 0
                dy != 0 && Board.isBoardEdge(y - dy * leftDown) -> 0
                Board.getStoneType(y - down, x - left) == OTHER_STONEType -> 0
                else -> 1
            }
        val rightUpValid =
            when {
                dx != 0 && Board.isBoardEdge(x + dx * rightUp) -> 0
                dy != 0 && Board.isBoardEdge(y + dy * rightUp) -> 0
                Board.getStoneType(y + up, x + right) == OTHER_STONEType -> 0
                else -> 1
            }

        return if (leftDownValid + rightUpValid >= 1) 1 else 0
    }
}
