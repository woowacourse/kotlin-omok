package woowacourse.omok.model.rule

import woowacourse.omok.model.board.OmokBoard
import woowacourse.omok.model.board.OmokBoardConfig.X_Edge
import woowacourse.omok.model.board.OmokBoardConfig.Y_Edge
import woowacourse.omok.model.board.Position

class RenjuRule(
    position: Position,
    omokBoard: OmokBoard,
) : OmokRule(position = position, omokBoard = omokBoard) {
    override fun validate(): Boolean = countOpenPatterns(4) >= 2 || countOpenPatterns(2) >= 2

    private fun countOpenPatterns(requiredStones: Int): Int = directions.sumOf { direction -> checkOpenPattern(direction, requiredStones) }

    private fun checkOpenPattern(
        direction: Pair<Int, Int>,
        requiredStones: Int,
    ): Int {
        val (x, y) = adaptedPoint
        val (dx, dy) = direction
        val oppositeDirection = Pair(-dx, -dy)

        val (stone1, blink1) = search(oppositeDirection)
        val (stone2, blink2) = search(direction)

        val leftDown = stone1 + blink1
        val left = dx * (leftDown + 1)
        val down = dy * (leftDown + 1)

        val rightUp = stone2 + blink2
        val right = dx * (rightUp + 1)
        val up = dy * (rightUp + 1)

        return when {
            stone1 + stone2 != requiredStones -> 0
            blink1 + blink2 == 2 -> 0
            dx != 0 && x - dx * leftDown in X_Edge -> 0
            dy != 0 && y - dy * leftDown in Y_Edge -> 0
            dx != 0 && x + dx * rightUp in X_Edge -> 0
            dy != 0 && y + dy * rightUp in Y_Edge -> 0
            adaptedBoard[y - down][x - left] == opponentStone -> 0
            adaptedBoard[y + up][x + right] == opponentStone -> 0
            countToWall(oppositeDirection) + countToWall(direction) <= 5 -> 0
            else -> 1
        }
    }
}
