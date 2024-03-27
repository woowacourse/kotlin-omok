package omok.model.rule

import omok.model.position.Position

object ForbiddenChecker {
    const val EXCEED_FIVE_PRECONDITION = 5
    private val renjuRules: List<RenjuRule> = listOf(DoubleThreeChecker, DoubleFourChecker, ExceedFiveChecker(EXCEED_FIVE_PRECONDITION))

    fun checkForbidden(position: Position): Boolean = renjuRules.any { it.check(position) }
}
