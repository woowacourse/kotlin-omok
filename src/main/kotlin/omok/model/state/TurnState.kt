package omok.model.state

import omok.model.Color
import omok.model.GameResult
import omok.model.Position
import omok.model.search.AscendingDfs
import omok.model.search.DescendingDfs
import omok.model.search.HorizontalDfs
import omok.model.search.VerticalDfs

abstract class TurnState(
    private val status: Array<Array<Color?>>,
) {
    abstract fun getWinningResult(
        position: Position,
        markSinglePlace: (row: Int, col: Int, color: Color) -> Unit,
        addSingleStone: (Color, Position) -> Unit,
    ): GameResult?

    protected abstract fun addStone(
        row: Int,
        col: Char,
        color: Color,
        position: Position,
        markSinglePlace: (row: Int, col: Int, color: Color) -> Unit,
        addSingleStone: (Color, Position) -> Unit,
    )

    protected fun isCurrentStoneWinner(
        position: Position,
        color: Color,
        markSinglePlace: (row: Int, col: Int, color: Color) -> Unit,
        addSingleStone: (Color, Position) -> Unit,
    ): Boolean {
        val row = ARRAY_SIZE - position.row.value
        addStone(row, position.col.title, color, position, markSinglePlace, addSingleStone)
        return calculateSearchResult(row, position.col.value, color)
    }

    private fun calculateSearchResult(
        row: Int,
        col: Int,
        color: Color,
    ): Boolean {
        val verticalCount = VerticalDfs(status).apply { search(color, row, col) }.count
        val horizontalCount = HorizontalDfs(status).apply { search(color, row, col) }.count
        val ascendingCount = AscendingDfs(status).apply { search(color, row, col) }.count
        val descendingCount = DescendingDfs(status).apply { search(color, row, col) }.count
        println(
            """
            color : $color
            verticalCount : $verticalCount
            horizontalCount : $horizontalCount
            ascendingCount : $ascendingCount
            descendingCount : $descendingCount
            """.trimIndent(),
        )
        return listOf(verticalCount, horizontalCount, ascendingCount, descendingCount).any { it >= 5 }
    }

    companion object {
        private const val ARRAY_SIZE = 16
    }
}
