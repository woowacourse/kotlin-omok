package omok.model.position

import omok.model.rule.RulesAdapter

data class Position(val row: Row, val column: Column) {
    fun checkForbidden(): Boolean = RulesAdapter.checkForbidden(this)
}
