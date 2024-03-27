package woowacourse.omok.domain.omok.model.search

import woowacourse.omok.domain.omok.model.Board
import woowacourse.omok.domain.omok.model.Color

sealed class Dfs(
    private val status: Array<Array<Color>>,
) {
    private val visited = Array(Board.ARRAY_SIZE) { Array(Board.ARRAY_SIZE) { false } }
    var count: Int = 0
        protected set

    abstract fun search(
        color: Color,
        row: Int,
        column: Int,
    )

    protected fun isVisitAble(
        color: Color,
        row: Int,
        column: Int,
    ): Boolean {
        return row in 1..Board.BOARD_SIZE &&
            column in 1..Board.BOARD_SIZE &&
            status[row][column] == color &&
            !visited[row][column]
    }

    protected fun visit(
        row: Int,
        column: Int,
    ) {
        visited[row][column] = true
        count++
    }
}
