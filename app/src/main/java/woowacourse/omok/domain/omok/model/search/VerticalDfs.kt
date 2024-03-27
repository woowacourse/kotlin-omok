package woowacourse.omok.domain.omok.model.search

import woowacourse.omok.domain.omok.model.Color

class VerticalDfs(
    status: Array<Array<Color>>,
) : Dfs(status) {
    override fun search(
        color: Color,
        row: Int,
        column: Int,
    ) {
        if (!isVisitAble(color, row, column)) return
        visit(row, column)
        search(color, row + 1, column)
        search(color, row - 1, column)
    }
}
