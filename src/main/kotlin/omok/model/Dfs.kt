package omok.model

abstract class Dfs(
    private val status: Array<Array<Color?>>,
) {
    private val visited = Array(15) { Array(15) { false } }
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
        row in 0..14 &&
            column in 0..14 &&
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

class VerticalDfs(
    status: Array<Array<Color?>>,
) : Dfs(status) {
    override fun search(
        color: Color,
        row: Int,
        column: Int,
    ) {
        if (!isVisitedPosition(color, row, column)) return
        search(color, row + 1, column)
        search(color, row - 1, column)
    }
}

class HorizontalDfs(
    status: Array<Array<Color?>>,
) : Dfs(status) {
    override fun search(
        color: Color,
        row: Int,
        column: Int,
    ) {
        if (!isVisitedPosition(color, row, column)) return
        search(color, row, column - 1)
        search(color, row, column + 1)
    }
}

class AscendingDfs(
    status: Array<Array<Color?>>,
) : Dfs(status) {
    override fun search(
        color: Color,
        row: Int,
        column: Int,
    ) {
        if (!isVisitedPosition(color, row, column)) return
        search(color, row - 1, column + 1)
        search(color, row + 1, column - 1)
    }
}

class DescendingDfs(
    status: Array<Array<Color?>>,
) : Dfs(status) {
    override fun search(
        color: Color,
        row: Int,
        column: Int,
    ) {
        if (!isVisitedPosition(color, row, column)) return
        search(color, row - 1, column - 1)
        search(color, row + 1, column + 1)
    }
}
