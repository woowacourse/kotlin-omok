package omok.model.rule

import omok.model.board.Board
import omok.model.position.DeltaPosition
import omok.model.position.Position

object DoubleFourChecker : RenjuRule(Board.board) {
    private const val OPEN_FOUR_NOT_FOUND = 0
    private const val OPEN_FOUR_FOUND = 1
    private const val MIN_BLINKS_OPEN_FOUR = 2
    private const val INVALID_OPEN_FOUR_COUNT = 2
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
            blink1 + blink2 == MIN_BLINKS_OPEN_FOUR && stone1 + stone2 == STONES_FOR_WIN -> return INVALID_OPEN_FOUR_COUNT
            blink1 + blink2 == MIN_BLINKS_OPEN_FOUR && stone1 + stone2 == MAX_STONES_OPEN_FOUR -> return INVALID_OPEN_FOUR_COUNT
            stone1 + stone2 != REQUIRED_STONES -> return OPEN_FOUR_NOT_FOUND
            blink1 + blink2 == MIN_BLINKS_OPEN_FOUR -> return OPEN_FOUR_NOT_FOUND
        }

        val row = position.row.value
        val column = position.column.value
        val deltaRow = deltaPosition.deltaRow
        val deltaCol = deltaPosition.deltaColumn

        val leftDownValid =
            when {
                deltaRow != Board.MIN_AXIS && row - deltaRow * leftDown in listOf(Board.MIN_AXIS, Board.MAX_AXIS) -> OPEN_FOUR_NOT_FOUND
                deltaCol != Board.MIN_AXIS && column - deltaCol * leftDown in listOf(Board.MIN_AXIS, Board.MAX_AXIS) -> OPEN_FOUR_NOT_FOUND
                Board.board[column - down][row - left] == OTHER_STONE -> OPEN_FOUR_NOT_FOUND
                else -> OPEN_FOUR_FOUND
            }
        val rightUpValid =
            when {
                deltaRow != Board.MIN_AXIS && row + (deltaRow * rightUp) in listOf(Board.MIN_AXIS, Board.MAX_AXIS) -> OPEN_FOUR_NOT_FOUND
                deltaCol != Board.MIN_AXIS && column + (deltaCol * rightUp) in listOf(Board.MIN_AXIS, Board.MAX_AXIS) -> OPEN_FOUR_NOT_FOUND
                Board.board[column + up][row + right] == OTHER_STONE -> OPEN_FOUR_NOT_FOUND
                else -> OPEN_FOUR_FOUND
            }

        return if (leftDownValid + rightUpValid >= OPEN_FOUR_FOUND) OPEN_FOUR_FOUND else OPEN_FOUR_NOT_FOUND
    }
}
