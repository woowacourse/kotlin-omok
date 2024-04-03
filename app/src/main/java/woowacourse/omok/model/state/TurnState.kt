package woowacourse.omok.model.state

import woowacourse.omok.model.Color
import woowacourse.omok.model.GameResult
import woowacourse.omok.model.Position
import woowacourse.omok.model.Rows
import woowacourse.omok.model.search.AscendingFiveInRowSearch
import woowacourse.omok.model.search.DescendingFiveInRowSearch
import woowacourse.omok.model.search.HorizontalFiveInRowSearch
import woowacourse.omok.model.search.VerticalFiveInRowSearch

abstract class TurnState(
    private val status: Rows,
) {
    abstract fun getWinningResult(
        position: Position,
        markSinglePlace: (horizontalCoordinate: Int, verticalCoordinate: Int, color: Color) -> Unit,
    ): GameResult?

    protected fun isCurrentStoneWinner(
        position: Position,
        color: Color,
        markSinglePlace: (horizontalCoordinate: Int, verticalCoordinate: Int, color: Color) -> Unit,
    ): Boolean {
        val horizontalCoordinate = COMPUTATION_BOARD_SIZE - position.horizontalCoordinate
        addStone(color, position, markSinglePlace)
        return calculateSearchResult(horizontalCoordinate, position.verticalCoordinate, color)
    }

    protected abstract fun addStone(
        color: Color,
        position: Position,
        markSinglePlace: (horizontalCoordinate: Int, verticalCoordinate: Int, color: Color) -> Unit,
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
