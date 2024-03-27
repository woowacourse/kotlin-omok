package omok.model.rule

import omok.model.board.Board
import omok.model.position.Direction
import omok.model.position.Position

class ExceedFiveChecker(private val exceedFivePrecondition: Int) : RenjuRule(Board.board) {
    override fun check(position: Position): Boolean =
        Direction.types.map {
                direction ->
            isMoreThanFive(position, Direction(direction.row, direction.column))
        }.contains(true)

    private fun isMoreThanFive(
        position: Position,
        direction: Direction,
    ): Boolean {
        val (stone1, blink1) = search(position, -direction)
        val (stone2, blink2) = search(position, direction)

        return when {
            blink1 + blink2 == MIN_BLINKS_EXCEED_FIVE && stone1 + stone2 >= exceedFivePrecondition -> true
            else -> false
        }
    }

    companion object {
        private const val MIN_BLINKS_EXCEED_FIVE = 0
    }
}
