package omok.model.rule

import omok.model.board.Board
import omok.model.position.DeltaPosition
import omok.model.position.Position

object DoubleFourChecker : RenjuRule(Board.board) {
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
        val down = deltaPosition.deltaCol * (leftDown + 1)

        val rightUp = stone2 + blink2
        val right = deltaPosition.deltaRow * (rightUp + 1)
        val up = deltaPosition.deltaCol * (rightUp + 1)

        when {
            blink1 + blink2 == 2 && stone1 + stone2 == 4 -> return 2
            blink1 + blink2 == 2 && stone1 + stone2 == 5 -> return 2
            stone1 + stone2 != 3 -> return 0
            blink1 + blink2 == 2 -> return 0
        }

        val row = position.row.value
        val col = position.col.value
        val deltaRow = deltaPosition.deltaRow
        val deltaCol = deltaPosition.deltaCol

        val leftDownValid =
            when {
                deltaRow != 0 && row - deltaRow * leftDown in listOf(MIN_X, Board.BOARD_SIZE - 1) -> 0
                deltaCol != 0 && col - deltaCol * leftDown in listOf(MIN_Y, Board.BOARD_SIZE - 1) -> 0
                Board.board[col - down][row - left] == OTHER_STONE -> 0
                else -> 1
            }
        val rightUpValid =
            when {
                deltaRow != 0 && row + (deltaRow * rightUp) in listOf(MIN_X, Board.BOARD_SIZE - 1) -> 0
                deltaCol != 0 && col + (deltaCol * rightUp) in listOf(MIN_Y, Board.BOARD_SIZE - 1) -> 0
                Board.board[col + up][row + right] == OTHER_STONE -> 0
                else -> 1
            }

        return if (leftDownValid + rightUpValid >= 1) 1 else 0
    }
}
