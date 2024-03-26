package omok.model.rule

import omok.model.board.Board
import omok.model.position.DeltaPosition
import omok.model.position.Position

object ExceedFiveChecker : RenjuRule(Board.board) {
    private const val MIN_BLINKS_EXCEED_FIVE = 0
    private const val EXCEED_FIVE_PRECONDITION = 5

    fun isMoreThanFive(position: Position): Boolean =
        directions.map { direction -> isMoreThanFive(position, DeltaPosition(direction[0], direction[1])) }.contains(true)

    private fun isMoreThanFive(
        position: Position,
        deltaPosition: DeltaPosition,
    ): Boolean {
        val (stone1, blink1) = search(position, -deltaPosition)
        val (stone2, blink2) = search(position, deltaPosition)

        return when {
            blink1 + blink2 == MIN_BLINKS_EXCEED_FIVE && stone1 + stone2 >= EXCEED_FIVE_PRECONDITION -> true
            else -> false
        }
    }
}
