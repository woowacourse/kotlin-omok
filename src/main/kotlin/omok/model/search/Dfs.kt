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
        row: Int,
        column: Int,
    )

    protected fun isVisitedPosition(
        color: Color,
        row: Int,
        column: Int,
    ): Boolean {
        if (!isVisitAvailable(row, column, color)) return false
        visit(row, column)
        return true
    }

    private fun isVisitAvailable(
        row: Int,
        column: Int,
        color: Color,
    ): Boolean =
        row in VISIT_INDEX_RANGE &&
            column in VISIT_INDEX_RANGE &&
            status[row][column] == color &&
            !visited[row][column]

    private fun visit(
        row: Int,
        column: Int,
    ) {
        visited[row][column] = true
        count++
    }

    companion object {
        private const val COMPUTATION_BOARD_SIZE = 16
        private val VISIT_INDEX_RANGE = 1..15
    }
}
