package omok.model.rule

import omok.model.board.Board
import omok.model.position.Direction
import omok.model.position.Position

object ExceedFiveChecker : RenjuRule(Board.board) {
    private const val MIN_BLINKS_EXCEED_FIVE = 0

    fun isMoreThanFive(
        position: Position,
        exceedFivePrecondition: Int,
    ): Boolean =
        Direction.types.map {
                direction ->
            isMoreThanFive(position, Direction(direction.rowDirection, direction.columnDirection), exceedFivePrecondition)
        }.contains(true)

    private fun isMoreThanFive(
        position: Position,
        direction: Direction,
        exceedFivePrecondition: Int,
    ): Boolean {
        val (stone1, blink1) = search(position, -direction)
        val (stone2, blink2) = search(position, direction)

        return when {
            blink1 + blink2 == MIN_BLINKS_EXCEED_FIVE && stone1 + stone2 >= exceedFivePrecondition -> true
            else -> false
        }
    }
}
