package omok.model.rule.library

import kotlin.properties.Delegates

class OverlineRule : OmokRule() {
    var count by Delegates.notNull<Int>()

    override fun abide(
        board: List<List<Int>>,
        position: Pair<Int, Int>,
    ): Boolean = directions.all { direction -> !checkOverline(board, position, direction) }

    private fun checkOverline(
        board: List<List<Int>>,
        position: Pair<Int, Int>,
        direction: Pair<Int, Int>,
    ): Boolean {
        val oppositeDirection = direction.let { (dx, dy) -> Pair(-dx, -dy) }
        val (stone1, blink1) = search(board, position, oppositeDirection)
        val (stone2, blink2) = search(board, position, direction)

        return when {
            blink1 + blink2 == 0 && stone1 + stone2 >= count -> true
            else -> false
        }
    }
}
