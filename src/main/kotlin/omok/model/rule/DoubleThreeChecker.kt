package omok.model.rule

import omok.model.board.Board
import omok.model.position.DeltaPosition
import omok.model.position.Position

object DoubleThreeChecker : RenjuRule(Board.board) {
    fun isDoubleThree(position: Position): Boolean =
        directions.sumOf { direction -> checkOpenThree(position, DeltaPosition(direction[0], direction[1])) } >= 2

    private fun checkOpenThree(
        position: Position,
        deltaPosition: DeltaPosition,
    ): Int {
        val (stone1, blink1) = search(position, -deltaPosition)
        val (stone2, blink2) = search(position, deltaPosition)

        val leftDown = stone1 + blink1
        val rightUp = stone2 + blink2

        return when {
            isValidStoneCount(stone1, stone2) -> 0
            isValidBlinkCount(blink1, blink2) -> 0
            isAtBoardEdge(position, deltaPosition, leftDown, rightUp) -> 0
            isBesideAnotherStone(position, deltaPosition, leftDown, rightUp) -> 0
            countToWall(position, -deltaPosition) + countToWall(position, deltaPosition) <= 5 -> 0
            else -> 1
        }
    }

    private fun isValidStoneCount(
        stone1: Int,
        stone2: Int,
    ): Boolean =
        when {
            stone1 + stone2 != 2 -> true
            else -> false
        }

    private fun isValidBlinkCount(
        blink1: Int,
        blink2: Int,
    ): Boolean =
        when {
            blink1 + blink2 == 2 -> true
            else -> false
        }

    private fun isAtBoardEdge(
        position: Position,
        deltaPosition: DeltaPosition,
        leftDown: Int,
        rightUp: Int,
    ): Boolean {
        val row = position.row.value
        val column = position.column.value
        val deltaRow = deltaPosition.deltaRow
        val deltaCol = deltaPosition.deltaColumn
        return when {
            deltaRow != 0 && row - leftDown in listOf(MIN_X, Board.BOARD_SIZE - 1) -> true
            deltaCol != 0 && column - leftDown in listOf(MIN_Y, Board.BOARD_SIZE - 1) -> true
            deltaRow != 0 && row + rightUp in listOf(MIN_X, Board.BOARD_SIZE - 1) -> true
            deltaCol != 0 && column + rightUp in listOf(MIN_Y, Board.BOARD_SIZE - 1) -> true
            else -> false
        }
    }

    private fun isBesideAnotherStone(
        position: Position,
        deltaPosition: DeltaPosition,
        leftDown: Int,
        rightUp: Int,
    ): Boolean {
        val row = position.row.value
        val column = position.column.value
        val deltaRow = deltaPosition.deltaRow
        val deltaCol = deltaPosition.deltaColumn

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
        deltaPosition: DeltaPosition,
    ): Int {
        var toRight = position.row.value
        var toTop = position.column.value
        var distance = 0
        while (true) {
            if (isBoardRange(deltaPosition, toRight, toTop)) break
            toRight += deltaPosition.deltaRow
            toTop += deltaPosition.deltaColumn
            when (Board.board[toTop][toRight]) {
                in listOf(CURRENT_STONE, EMPTY_STONE) -> distance++
                OTHER_STONE -> break
                else -> throw IllegalArgumentException()
            }
        }
        return distance
    }
}
