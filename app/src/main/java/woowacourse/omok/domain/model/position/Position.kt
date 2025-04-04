package woowacourse.omok.domain.model.position

import woowacourse.omok.domain.model.Board

data class Position(val column: Column, val row: Row) {
    constructor(column: Int, row: Int, board: Board) : this(
        Column.from(column, board.column),
        Row.from(row, board.row),
    )
}
