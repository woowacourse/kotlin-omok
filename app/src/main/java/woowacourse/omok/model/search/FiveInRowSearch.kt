package woowacourse.omok.model.search

import woowacourse.omok.model.Color
import woowacourse.omok.model.Rows

abstract class FiveInRowSearch(
    private val status: Rows,
) {
    private val visited = Array(COMPUTATION_BOARD_SIZE) { Array(COMPUTATION_BOARD_SIZE) { false } }
    var count: Int = 0
        protected set

    abstract fun search(
        color: Color,
        horizontalCoordinate: Int,
        verticalCoordinate: Int,
    )

    protected fun isVisitedPosition(
        color: Color,
        horizontalCoordinate: Int,
        verticalCoordinate: Int,
    ): Boolean {
        if (!isVisitAvailable(horizontalCoordinate, verticalCoordinate, color)) return false
        visit(horizontalCoordinate, verticalCoordinate)
        return true
    }

    private fun isVisitAvailable(
        horizontalCoordinate: Int,
        verticalCoordinate: Int,
        color: Color,
    ): Boolean =
        horizontalCoordinate in VISIT_INDEX_RANGE &&
            verticalCoordinate in VISIT_INDEX_RANGE &&
            status.values[horizontalCoordinate].placementData[verticalCoordinate] == color &&
            !visited[horizontalCoordinate][verticalCoordinate]

    private fun visit(
        horizontalCoordinate: Int,
        verticalCoordinate: Int,
    ) {
        visited[horizontalCoordinate][verticalCoordinate] = true
        count++
    }

    companion object {
        private const val COMPUTATION_BOARD_SIZE = 16
        private val VISIT_INDEX_RANGE = 1..15
    }
}
