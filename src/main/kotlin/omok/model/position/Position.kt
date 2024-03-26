package omok.model.position

import omok.model.rule.DoubleFourChecker
import omok.model.rule.DoubleThreeChecker
import omok.model.rule.ExceedFiveChecker

data class Position(val row: Row, val column: Column) {
    override fun toString(): String = "${Row.X_AXIS_START + row.value}${column.value + 1}"

    fun checkForbidden(): Boolean =
        DoubleThreeChecker.isDoubleThree(this) ||
            DoubleFourChecker.isDoubleFour(this) ||
            ExceedFiveChecker.isMoreThanFive(this, EXCEED_FIVE_PRECONDITION)

    companion object {
        private const val EXCEED_FIVE_PRECONDITION = 5
    }
}
