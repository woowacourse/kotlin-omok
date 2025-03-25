package omok.model.rule

import omok.model.board.OmokBoard
import omok.model.board.Position

class ThreeThreeRule(
    position: Position,
    omokBoard: OmokBoard,
) : OmokRule(position = position, omokBoard = omokBoard) {
    override fun validate(): Boolean = countOpenThrees() >= 2

    private fun countOpenThrees(): Int = directions.sumOf { direction -> checkOpenThree(direction) }

    private fun checkOpenThree(direction: Pair<Int, Int>): Int {
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

        return when {
            stone1 + stone2 != 2 -> 0
            blink1 + blink2 == 2 -> 0
            dx != 0 && x - dx * leftDown in X_Edge -> 0
            dy != 0 && y - dy * leftDown in Y_Edge -> 0
            dx != 0 && x + dx * rightUp in X_Edge -> 0
            dy != 0 && y + dy * rightUp in Y_Edge -> 0
            adaptedBoard[y - down][x - left] == WHITE_STONE -> 0
            adaptedBoard[y + up][x + right] == WHITE_STONE -> 0
            countToWall(oppositeDirection) + countToWall(direction) <= 5 -> 0
            else -> 1
        }
    }
}
