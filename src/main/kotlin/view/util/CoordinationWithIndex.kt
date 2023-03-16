package view.util

object CoordinationWithIndex {
    fun convertCoordination(dot: String): Pair<Int, Int> {
        val row = convertRowIndex(dot.substring(1).toInt())
        val col = convertColIndex(dot[0].hashCode())

        return row to col
    }

    private fun convertRowIndex(row: Int): Int = 15 - row
    private fun convertColIndex(col: Int): Int = col - 65
}
