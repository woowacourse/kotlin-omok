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
        if (!isVisitedPosition(color, row, column)) return
        search(color, row, column - 1)
        search(color, row, column + 1)
    }
}
