package omok.model.state

import omok.model.Color
import omok.model.GameResult
import omok.model.Position
import omok.model.search.AscendingFiveInRowSearch
import omok.model.search.DescendingFiveInRowSearch
import omok.model.search.HorizontalFiveInRowSearch
import omok.model.search.VerticalFiveInRowSearch

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
        val horizontalCoordinate = COMPUTATION_BOARD_SIZE - position.horizontalCoordinate.index
        addStone(color, position, markSinglePlace, addSingleStone)
        return calculateSearchResult(horizontalCoordinate, position.verticalCoordinate.index, color)
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
        val verticalCount = VerticalFiveInRowSearch(status).apply { search(color, horizontalCoordinate, verticalCoordinate) }.count
        val horizontalCount = HorizontalFiveInRowSearch(status).apply { search(color, horizontalCoordinate, verticalCoordinate) }.count
        val ascendingCount = AscendingFiveInRowSearch(status).apply { search(color, horizontalCoordinate, verticalCoordinate) }.count
        val descendingCount = DescendingFiveInRowSearch(status).apply { search(color, horizontalCoordinate, verticalCoordinate) }.count
        return listOf(verticalCount, horizontalCount, ascendingCount, descendingCount).any { it >= 5 }
    }

    companion object {
        const val COMPUTATION_BOARD_SIZE = 16
    }
}
