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

        val leftDownValid =
            when {
                deltaPosition.deltaRow != 0 && position.row - deltaPosition.deltaRow * leftDown in listOf(MIN_X, Board.BOARD_SIZE - 1) -> 0
                deltaPosition.deltaCol != 0 && position.col - deltaPosition.deltaCol * leftDown in listOf(MIN_Y, Board.BOARD_SIZE - 1) -> 0
                Board.board[position.col - down][position.row - left] == OTHER_STONE -> 0
                else -> 1
            }
        val rightUpValid =
            when {
                deltaPosition.deltaRow != 0 && position.row + (deltaPosition.deltaRow * rightUp) in listOf(MIN_X, Board.BOARD_SIZE - 1) -> 0
                deltaPosition.deltaCol != 0 && position.col + (deltaPosition.deltaCol * rightUp) in listOf(MIN_Y, Board.BOARD_SIZE - 1) -> 0
                Board.board[position.col + up][position.row + right] == OTHER_STONE -> 0
                else -> 1
            }

        return if (leftDownValid + rightUpValid >= 1) 1 else 0
    }
}
