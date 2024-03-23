package omok.model.position

data class Position(val row: Row, val col: Col) {
    override fun toString(): String = "${Row.X_AXIS_START + row.value}${col.value + 1}"
}
