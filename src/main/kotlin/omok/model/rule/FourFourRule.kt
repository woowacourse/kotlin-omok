package omok.model.rule

import omok.model.board.OmokBoard
import omok.model.board.Position

class FourFourRule(
    position: Position,
    omokBoard: OmokBoard,
) : OmokRule(position = position, omokBoard = omokBoard) {
    override fun validate(): Boolean = countOpenThrees() >= 2

    private fun countOpenThrees(): Int = directions.sumOf { direction -> checkOpenFour(direction) }

    private fun checkOpenFour(direction: Pair<Int, Int>): Int {
        val (x, y) = adaptedPoint
        val (dx, dy) = direction
        val oppositeDirection = direction.let { (dx, dy) -> Pair(-dx, -dy) }

        val (stone1, blink1) = search(oppositeDirection)
        val (stone2, blink2) = search(direction)

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
                dx != 0 && x - dx * leftDown in X_Edge -> 0
                dy != 0 && y - dy * leftDown in Y_Edge -> 0
                adaptedBoard[y - down][x - left] == opponentStone -> 0
                else -> 1
            }
        val rightUpValid =
            when {
                dx != 0 && x + (dx * rightUp) in X_Edge -> 0
                dy != 0 && y + (dy * rightUp) in Y_Edge -> 0
                adaptedBoard[y + up][x + right] == opponentStone -> 0
                else -> 1
            }

        return if (leftDownValid + rightUpValid >= 1) 1 else 0
    }
}
