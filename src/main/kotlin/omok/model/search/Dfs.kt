package omok.model.search

import omok.model.Color

abstract class Dfs(
    private val status: Array<Array<Color?>>,
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
            status[horizontalCoordinate][verticalCoordinate] == color &&
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
