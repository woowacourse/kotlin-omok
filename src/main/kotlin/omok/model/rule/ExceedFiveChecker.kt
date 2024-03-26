package omok.model.rule

import omok.model.board.Board
import omok.model.position.DeltaPosition
import omok.model.position.Position

object ExceedFiveChecker : RenjuRule(Board.board) {
    private const val MIN_BLINKS_EXCEED_FIVE = 0

    fun isMoreThanFive(
        position: Position,
        exceedFivePrecondition: Int,
    ): Boolean =
        directions.map {
                direction ->
            isMoreThanFive(position, DeltaPosition(direction[0], direction[1]), exceedFivePrecondition)
        }.contains(true)

    private fun isMoreThanFive(
        position: Position,
        deltaPosition: DeltaPosition,
        exceedFivePrecondition: Int,
    ): Boolean {
        val (stone1, blink1) = search(position, -deltaPosition)
        val (stone2, blink2) = search(position, deltaPosition)

        return when {
            blink1 + blink2 == MIN_BLINKS_EXCEED_FIVE && stone1 + stone2 >= exceedFivePrecondition -> true
            else -> false
        }
    }
}
