package woowacourse.omok.model.rule

import woowacourse.omok.model.board.OmokBoard
import woowacourse.omok.model.board.OmokBoardConfig.CANNOT_PLACE_STONE
import woowacourse.omok.model.board.OmokBoardConfig.CAN_PLACE_STONE
import woowacourse.omok.model.board.OmokBoardConfig.FOUR_STONE_PATTERN
import woowacourse.omok.model.board.OmokBoardConfig.NEXT_POSITION
import woowacourse.omok.model.board.OmokBoardConfig.TWO_STONE_PATTERN
import woowacourse.omok.model.board.OmokBoardConfig.WINNING_LINE_LENGTH
import woowacourse.omok.model.board.OmokBoardConfig.X_Edge
import woowacourse.omok.model.board.OmokBoardConfig.Y_Edge
import woowacourse.omok.model.board.OmokBoardConfig.ZERO
import woowacourse.omok.model.board.Position

class RenjuRule(
    position: Position,
    omokBoard: OmokBoard,
) : OmokRule(position = position, omokBoard = omokBoard) {
    fun validateDoubleThree(): Boolean = countOpenPatterns(TWO_STONE_PATTERN) >= TWO_STONE_PATTERN

    fun validateDoubleFour(): Boolean = countOpenPatterns(FOUR_STONE_PATTERN) >= TWO_STONE_PATTERN

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
        val left = dx * (leftDown + NEXT_POSITION)
        val down = dy * (leftDown + NEXT_POSITION)

        val rightUp = stone2 + blink2
        val right = dx * (rightUp + NEXT_POSITION)
        val up = dy * (rightUp + NEXT_POSITION)

        return when {
            stone1 + stone2 != requiredStones -> CANNOT_PLACE_STONE
            blink1 + blink2 == TWO_STONE_PATTERN -> CANNOT_PLACE_STONE
            dx != ZERO && x - dx * leftDown in X_Edge -> CANNOT_PLACE_STONE
            dy != ZERO && y - dy * leftDown in Y_Edge -> CANNOT_PLACE_STONE
            dx != ZERO && x + dx * rightUp in X_Edge -> CANNOT_PLACE_STONE
            dy != ZERO && y + dy * rightUp in Y_Edge -> CANNOT_PLACE_STONE
            adaptedBoard[y - down][x - left] == opponentStone -> CANNOT_PLACE_STONE
            adaptedBoard[y + up][x + right] == opponentStone -> CANNOT_PLACE_STONE
            countToWall(oppositeDirection) + countToWall(direction) <= WINNING_LINE_LENGTH -> CANNOT_PLACE_STONE
            else -> CAN_PLACE_STONE
        }
    }
}
