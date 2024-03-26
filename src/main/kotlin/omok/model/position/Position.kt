package omok.model.position

import omok.model.rule.RulesAdapter

data class Position(val row: Row, val column: Column) {
    override fun toString(): String = "${Row.X_AXIS_START + row.value}${column.value + 1}"

    fun checkForbidden(): Boolean = RulesAdapter.checkForbidden(this)
}
