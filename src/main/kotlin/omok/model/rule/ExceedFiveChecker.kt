package omok.model.rule

import omok.model.board.Board
import omok.model.position.DeltaPosition
import omok.model.position.Position

object ExceedFiveChecker : RenjuRule(Board.board) {
    fun isMoreThanFive(position: Position): Boolean =
        directions.map { direction -> isMoreThanFive(position, DeltaPosition(direction[0], direction[1])) }.contains(true)

    private fun isMoreThanFive(
        position: Position,
        deltaPosition: DeltaPosition,
    ): Boolean {
        val (stone1, blink1) = search(position, -deltaPosition)
        val (stone2, blink2) = search(position, deltaPosition)

        return when {
            blink1 + blink2 == 0 && stone1 + stone2 > 4 -> true
            else -> false
        }
    }
}
