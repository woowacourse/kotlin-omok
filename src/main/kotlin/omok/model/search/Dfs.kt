package omok.model.search

import omok.model.Color

abstract class Dfs(
    private val status: Array<Array<Color>>,
) {
    private val visited = Array(16) { Array(16) { false } }
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
        row in 1..15 &&
            column in 1..15 &&
            status[row][column] == color &&
            !visited[row][column]

    private fun visit(
        row: Int,
        column: Int,
    ) {
        visited[row][column] = true
        count++
    }
}
