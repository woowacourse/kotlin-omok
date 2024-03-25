package omok.model.rule

import omok.model.board.Board
import omok.model.position.DeltaPosition
import omok.model.position.Position

object DoubleFourChecker : RenjuRule(Board.board) {
    private const val OPEN_FOUR_NOT_FOUND = 0
    private const val MIN_BLINKS_OPEN_FOUR = 2
    private const val OPEN_FOUR_FOUND = 2
    private const val REQUIRED_STONES = 3
    private const val STONES_FOR_WIN = 4
    private const val MAX_STONES_OPEN_FOUR = 5

    fun isDoubleFour(position: Position): Boolean =
        directions.sumOf { direction -> checkOpenFour(position, DeltaPosition(direction[0], direction[1])) } >= 2

    private fun checkOpenFour(
        position: Position,
        deltaPosition: DeltaPosition,
    ): Int {
        val (stone1, blink1) = search(position, -deltaPosition)
        val (stone2, blink2) = search(position, deltaPosition)

        val leftDown = stone1 + blink1
        val left = deltaPosition.deltaRow * (leftDown + 1)
        val down = deltaPosition.deltaColumn * (leftDown + 1)

        val rightUp = stone2 + blink2
        val right = deltaPosition.deltaRow * (rightUp + 1)
        val up = deltaPosition.deltaColumn * (rightUp + 1)

        when {
            blink1 + blink2 == MIN_BLINKS_OPEN_FOUR && stone1 + stone2 == STONES_FOR_WIN -> return OPEN_FOUR_FOUND
            blink1 + blink2 == MIN_BLINKS_OPEN_FOUR && stone1 + stone2 == MAX_STONES_OPEN_FOUR -> return OPEN_FOUR_FOUND
            stone1 + stone2 != REQUIRED_STONES -> return OPEN_FOUR_NOT_FOUND
            blink1 + blink2 == MIN_BLINKS_OPEN_FOUR -> return OPEN_FOUR_NOT_FOUND
        }

        val row = position.row.value
        val column = position.column.value
        val deltaRow = deltaPosition.deltaRow
        val deltaCol = deltaPosition.deltaColumn

        val leftDownValid =
            when {
                deltaRow != 0 && row - deltaRow * leftDown in listOf(MIN_X, Board.BOARD_SIZE - 1) -> 0
                deltaCol != 0 && column - deltaCol * leftDown in listOf(MIN_Y, Board.BOARD_SIZE - 1) -> 0
                Board.board[column - down][row - left] == OTHER_STONE -> 0
                else -> 1
            }
        val rightUpValid =
            when {
                deltaRow != 0 && row + (deltaRow * rightUp) in listOf(MIN_X, Board.BOARD_SIZE - 1) -> 0
                deltaCol != 0 && column + (deltaCol * rightUp) in listOf(MIN_Y, Board.BOARD_SIZE - 1) -> 0
                Board.board[column + up][row + right] == OTHER_STONE -> 0
                else -> 1
            }

        return if (leftDownValid + rightUpValid >= 1) 1 else 0
    }
}
