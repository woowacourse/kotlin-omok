package omok.model.rule

import omok.model.board.Board

object ThreeThreeChecker : OmokRule() {
    fun checkThreeThree(
        x: Int,
        y: Int,
    ): Boolean =
        directions.sumOf { direction ->
            checkOpenThree(x, y, direction.relativeRowDirection, direction.relativeColumnDirection)
        } >= 2

    private fun checkOpenThree(
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

        return when {
            stone1 + stone2 != 2 -> 0
            blink1 + blink2 == 2 -> 0
            dx != 0 && Board.isBoardEdge(x - leftDown) -> 0
            dy != 0 && Board.isBoardEdge(y - leftDown) -> 0
            dx != 0 && Board.isBoardEdge(x + rightUp) -> 0
            dy != 0 && Board.isBoardEdge(y + rightUp) -> 0

            Board.getStoneType(y - down, x - left) == OTHER_STONEType -> 0
            Board.getStoneType(y + up, x + right) == OTHER_STONEType -> 0
            countToWall(x, y, -dx, -dy) + countToWall(x, y, dx, dy) <= 5 -> 0
            else -> 1
        }
    }

    private fun countToWall(
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
    ): Int {
        var toRight = x
        var toTop = y
        var distance = 0
        while (true) {
            if (dx > 0 && Board.isBoardEdge(toRight)) break
            if (dx < 0 && Board.isBoardEdge(toRight)) break
            if (dy > 0 && Board.isBoardEdge(toTop)) break
            if (dy < 0 && Board.isBoardEdge(toTop)) break
            toRight += dx
            toTop += dy
            when (Board.getStoneType(toTop, toRight)) {
                in listOf(CURRENT_STONEType, EMPTY_STONEType) -> distance++
                OTHER_STONEType -> break
                else -> throw IllegalArgumentException()
            }
        }
        return distance
    }
}
