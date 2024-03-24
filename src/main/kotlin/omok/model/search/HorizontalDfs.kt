package omok.model.search

import omok.model.Color

class HorizontalDfs(
    status: Array<Array<Color>>,
) : Dfs(status) {
    override fun search(
        color: Color,
        row: Int,
        column: Int,
    ) {
        if (!isVisitAble(color, row, column)) return
        visit(row, column)
        search(color, row, column - 1)
        search(color, row, column + 1)
    }
}
