package view.model

class PointModel(val row: String, val col: String) {
    override fun toString(): String = "$col$row"

    companion object {
        private const val ROW_START = 1
        private const val ROW_END = 15
        val ROW_RANGE: List<String> = (ROW_START..ROW_END).map(Int::toString)

        private const val COLUMN_START = 'A'
        private const val COLUMN_END = 'O'
        val COLUMN_RANGE: List<String> = (COLUMN_START..COLUMN_END).map(Char::toString)
    }
}
