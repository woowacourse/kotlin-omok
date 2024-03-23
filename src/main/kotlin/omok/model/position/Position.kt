package omok.model.position

import omok.model.rule.DoubleFourChecker
import omok.model.rule.DoubleThreeChecker
import omok.model.rule.ExceedFiveChecker

data class Position(val row: Row, val col: Col) {
    override fun toString(): String = "${Row.X_AXIS_START + row.value}${col.value + 1}"

    fun checkForbidden(): Boolean =
        DoubleThreeChecker.isDoubleThree(this) ||
            DoubleFourChecker.isDoubleFour(this) ||
            ExceedFiveChecker.isMoreThanFive(this)
}
