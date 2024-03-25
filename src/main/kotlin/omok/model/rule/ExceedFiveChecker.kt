package omok.model.rule

object ExceedFiveChecker : OmokRule() {
    private const val NO_SPACE = 0
    private const val EXCEED_STANDARD = 4

    fun checkMoreThanFive(
        x: Int,
        y: Int,
    ): Boolean =
        directions.map { direction ->
            checkMoreThanFive(x, y, direction.relativeRowDirection, direction.relativeColumnDirection)
        }.contains(true)

    private fun checkMoreThanFive(
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
    ): Boolean {
        val (stone1, blink1) = search(x, y, -dx, -dy)
        val (stone2, blink2) = search(x, y, dx, dy)

        return when {
            blink1 + blink2 == NO_SPACE && stone1 + stone2 > EXCEED_STANDARD -> true
            else -> false
        }
    }
}
