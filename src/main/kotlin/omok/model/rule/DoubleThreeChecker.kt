package omok.model.rule

import omok.model.board.Board
import omok.model.position.Direction
import omok.model.position.Position

object DoubleThreeChecker : RenjuRule(Board.board) {
    private const val OPEN_THREE_NOT_FOUND = 0
    private const val OPEN_THREE_FOUND = 1
    private const val INVALID_OPEN_THREE_COUNT = 2
    private const val MIN_BLINKS_OPEN_THREE = 2
    private const val MAX_STEPS_COUNT_WALL = 5

    fun isDoubleThree(position: Position): Boolean =
        Direction.types.sumOf {
                direction ->
            checkOpenThree(position, Direction(direction.rowDirection, direction.columnDirection))
        } >= INVALID_OPEN_THREE_COUNT

    private fun checkOpenThree(
        position: Position,
        direction: Direction,
    ): Int {
        val (stone1, blink1) = search(position, -direction)
        val (stone2, blink2) = search(position, direction)

        val leftDown = stone1 + blink1
        val rightUp = stone2 + blink2

        return when {
            isValidStoneCount(stone1, stone2) -> OPEN_THREE_NOT_FOUND
            isValidBlinkCount(blink1, blink2) -> OPEN_THREE_NOT_FOUND
            isAtBoardEdge(position, direction, leftDown, rightUp) -> OPEN_THREE_NOT_FOUND
            isBesideAnotherStone(position, direction, leftDown, rightUp) -> OPEN_THREE_NOT_FOUND
            countToWall(position, -direction) + countToWall(position, direction) <= MAX_STEPS_COUNT_WALL -> OPEN_THREE_NOT_FOUND
            else -> OPEN_THREE_FOUND
        }
    }

    private fun isValidStoneCount(
        stone1: Int,
        stone2: Int,
    ): Boolean =
        when {
            stone1 + stone2 != INVALID_OPEN_THREE_COUNT -> true
            else -> false
        }

    private fun isValidBlinkCount(
        blink1: Int,
        blink2: Int,
    ): Boolean =
        when {
            blink1 + blink2 == MIN_BLINKS_OPEN_THREE -> true
            else -> false
        }

    private fun isAtBoardEdge(
        position: Position,
        direction: Direction,
        leftDown: Int,
        rightUp: Int,
    ): Boolean {
        val row = position.row.value
        val column = position.column.value
        val deltaRow = direction.rowDirection
        val deltaCol = direction.columnDirection
        return when {
            deltaRow != Board.MIN_AXIS && row - leftDown in listOf(Board.MIN_AXIS, Board.MAX_AXIS) -> true
            deltaCol != Board.MIN_AXIS && column - leftDown in listOf(Board.MIN_AXIS, Board.MAX_AXIS) -> true
            deltaRow != Board.MIN_AXIS && row + rightUp in listOf(Board.MIN_AXIS, Board.MAX_AXIS) -> true
            deltaCol != Board.MIN_AXIS && column + rightUp in listOf(Board.MIN_AXIS, Board.MAX_AXIS) -> true
            else -> false
        }
    }

    private fun isBesideAnotherStone(
        position: Position,
        direction: Direction,
        leftDown: Int,
        rightUp: Int,
    ): Boolean {
        val row = position.row.value
        val column = position.column.value
        val deltaRow = direction.rowDirection
        val deltaCol = direction.columnDirection

        val left = deltaRow * (leftDown + 1)
        val down = deltaCol * (leftDown + 1)

        val right = deltaRow * (rightUp + 1)
        val up = deltaCol * (rightUp + 1)

        return when (OTHER_STONE) {
            Board.board[column - down][row - left] -> true
            Board.board[column + up][row + right] -> true
            else -> false
        }
    }

    private fun countToWall(
        position: Position,
        direction: Direction,
    ): Int {
        var toRight = position.row.value
        var toTop = position.column.value
        var distance = 0
        while (true) {
            if (isBoardRange(direction, toRight, toTop)) break
            toRight += direction.rowDirection
            toTop += direction.columnDirection
            when (Board.board[toTop][toRight]) {
                in listOf(CURRENT_STONE, EMPTY_STONE) -> distance++
                OTHER_STONE -> break
                else -> throw IllegalArgumentException()
            }
        }
        return distance
    }
}
