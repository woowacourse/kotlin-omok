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
    fun getWinningResult(
        position: Position,
        color: Color,
        placeStone: (Color, Position) -> Unit,
    ): GameResult? {
        if (isCurrentTurnWin(position, color, placeStone)) {
            return GameResult.entries.first { it.color == color }
        }
        return null
    }

    private fun isCurrentTurnWin(
        position: Position,
        color: Color,
        placeStone: (Color, Position) -> Unit,
    ): Boolean {
        addStone(position, placeStone)
        return calculateSearchResult(position, color)
    }

    protected abstract fun addStone(
        position: Position,
        placeStone: (Color, Position) -> Unit,
    )

    private fun calculateSearchResult(
        position: Position,
        color: Color,
    ): Boolean {
        val row = ARRAY_SIZE - position.row.value
        val col = position.col.value
        val verticalCount = VerticalDfs(status).apply { search(color, row, col) }.count
        val horizontalCount = HorizontalDfs(status).apply { search(color, row, col) }.count
        val ascendingCount = AscendingDfs(status).apply { search(color, row, col) }.count
        val descendingCount = DescendingDfs(status).apply { search(color, row, col) }.count
        return listOf(verticalCount, horizontalCount, ascendingCount, descendingCount).any { it >= 5 }
    }

    companion object {
        const val ARRAY_SIZE = 16
    }
}
