package omok.model.rule

import omok.model.position.Position

object RulesAdapter {
    private const val EXCEED_FIVE_PRECONDITION = 5

    fun checkForbidden(position: Position): Boolean =
        DoubleThreeChecker.isDoubleThree(position) ||
            DoubleFourChecker.isDoubleFour(position) ||
            ExceedFiveChecker.isMoreThanFive(position, EXCEED_FIVE_PRECONDITION)
}
