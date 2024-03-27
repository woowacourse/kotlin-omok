package omok.model.position

import omok.model.rule.ForbiddenChecker

data class Position(val row: Row, val column: Column) {
    fun checkForbidden(): Boolean = ForbiddenChecker.checkForbidden(this)
}
