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
        markSinglePlace: (horizontalCoordinate: Int, verticalCoordinate: Int, color: Color) -> Unit,
        addSingleStone: (Color, Position) -> Unit,
    ): GameResult?

    protected fun isCurrentStoneWinner(
        position: Position,
        color: Color,
        markSinglePlace: (horizontalCoordinate: Int, verticalCoordinate: Int, color: Color) -> Unit,
        addSingleStone: (Color, Position) -> Unit,
    ): Boolean {
        val horizontalCoordinate = COMPUTATION_BOARD_SIZE - position.horizontalCoordinate.value
        addStone(color, position, markSinglePlace, addSingleStone)
        return calculateSearchResult(horizontalCoordinate, position.verticalCoordinate.value, color)
    }

    protected abstract fun addStone(
        color: Color,
        position: Position,
        markSinglePlace: (horizontalCoordinate: Int, verticalCoordinate: Int, color: Color) -> Unit,
        addSingleStone: (Color, Position) -> Unit,
    )

    private fun calculateSearchResult(
        horizontalCoordinate: Int,
        verticalCoordinate: Int,
        color: Color,
    ): Boolean {
        val verticalCount = VerticalDfs(status).apply { search(color, horizontalCoordinate, verticalCoordinate) }.count
        val horizontalCount = HorizontalDfs(status).apply { search(color, horizontalCoordinate, verticalCoordinate) }.count
        val ascendingCount = AscendingDfs(status).apply { search(color, horizontalCoordinate, verticalCoordinate) }.count
        val descendingCount = DescendingDfs(status).apply { search(color, horizontalCoordinate, verticalCoordinate) }.count
        return listOf(verticalCount, horizontalCount, ascendingCount, descendingCount).any { it >= 5 }
    }

    companion object {
        const val COMPUTATION_BOARD_SIZE = 16
    }
}
