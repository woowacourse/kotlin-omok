package view.util

import model.domain.tools.Dot

object CoordinationWithDot {
    fun convertToDot(input: String): Dot {
        val row = convertRowIndex(input.substring(1).toInt())
        val col = convertColIndex(input[0].hashCode())

        return Dot(row, col)
    }

    private fun convertRowIndex(row: Int): Int = 15 - row
    private fun convertColIndex(col: Int): Int = col - 65
}
