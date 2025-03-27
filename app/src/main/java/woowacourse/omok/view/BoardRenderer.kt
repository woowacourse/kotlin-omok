package omok.view

import omok.domain.Board
import omok.domain.RowType

object BoardRenderer {
    fun render(board: Board): String {
        val rows =
            (15 downTo 1).map { rowNumber ->
                "${rowNumber.toString().padStart(2)} ${buildLine(rowNumber)}"
            }
        val columnLine = "   " + RowType.values().joinToString("  ") { it.name }
        return rows.joinToString("\n") + "\n" + columnLine
    }

    private fun buildLine(rowNumber: Int): String {
        val (left, middle, right) =
            when (rowNumber) {
                15 -> Triple("┌", "┬", "┐")
                1 -> Triple("└", "┴", "┘")
                else -> Triple("├", "┼", "┤")
            }
        return left + "──$middle──".repeat(13) + "──$right"
    }
}
