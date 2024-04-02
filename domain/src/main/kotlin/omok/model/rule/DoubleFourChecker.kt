package omok.model.rule

import omok.model.board.Board
import omok.model.position.Direction
import omok.model.position.Position
import omok.model.result.PutResult

object DoubleFourChecker : RenjuRule(Board.board) {
    private const val OPEN_FOUR_NOT_FOUND = 0
    private const val OPEN_FOUR_FOUND = 1
    private const val MIN_BLINKS_OPEN_FOUR = 2
    private const val INVALID_OPEN_FOUR_COUNT = 2
    private const val REQUIRED_STONES = 3
    private const val STONES_FOR_WIN = 4
    private const val MAX_STONES_OPEN_FOUR = 5

    override fun check(position: Position): PutResult {
        val isDoubleFour = Direction.types.sumOf { direction -> checkOpenFour(position, Direction(direction.row, direction.column)) } >= 2
        if (isDoubleFour) return PutResult.DoubleFour
        return PutResult.Running
    }

    private fun checkOpenFour(
        position: Position,
        direction: Direction,
    ): Int {
        val (stone1, blink1) = search(position, -direction)
        val (stone2, blink2) = search(position, direction)

        val rowDirection = direction.row.value
        val columnDirection = direction.column.value

        val leftDown = stone1 + blink1
        val left = rowDirection * (leftDown + 1)
        val down = columnDirection * (leftDown + 1)

        val rightUp = stone2 + blink2
        val right = rowDirection * (rightUp + 1)
        val up = columnDirection * (rightUp + 1)

        when {
            blink1 + blink2 == MIN_BLINKS_OPEN_FOUR && stone1 + stone2 == STONES_FOR_WIN -> return INVALID_OPEN_FOUR_COUNT
            blink1 + blink2 == MIN_BLINKS_OPEN_FOUR && stone1 + stone2 == MAX_STONES_OPEN_FOUR -> return INVALID_OPEN_FOUR_COUNT
            stone1 + stone2 != REQUIRED_STONES -> return OPEN_FOUR_NOT_FOUND
            blink1 + blink2 == MIN_BLINKS_OPEN_FOUR -> return OPEN_FOUR_NOT_FOUND
        }

        val row = position.row.value
        val column = position.column.value
        val axisRange = listOf(Board.MIN_AXIS, Board.MAX_AXIS)

        val leftDownValid =
            when {
                rowDirection != Board.MIN_AXIS && row - rowDirection * leftDown in axisRange -> OPEN_FOUR_NOT_FOUND
                columnDirection != Board.MIN_AXIS && column - columnDirection * leftDown in axisRange -> OPEN_FOUR_NOT_FOUND
                Board.board[column - down][row - left] == OTHER_STONE -> OPEN_FOUR_NOT_FOUND
                else -> OPEN_FOUR_FOUND
            }
        val rightUpValid =
            when {
                rowDirection != Board.MIN_AXIS && row + (rowDirection * rightUp) in axisRange -> OPEN_FOUR_NOT_FOUND
                columnDirection != Board.MIN_AXIS && column + (columnDirection * rightUp) in axisRange -> OPEN_FOUR_NOT_FOUND
                Board.board[column + up][row + right] == OTHER_STONE -> OPEN_FOUR_NOT_FOUND
                else -> OPEN_FOUR_FOUND
            }

        return if (leftDownValid + rightUpValid >= OPEN_FOUR_FOUND) OPEN_FOUR_FOUND else OPEN_FOUR_NOT_FOUND
    }
}
