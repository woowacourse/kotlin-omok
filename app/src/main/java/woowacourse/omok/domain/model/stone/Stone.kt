package woowacourse.omok.domain.model.stone

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.position.Position

data class Stone(
    val position: Position,
    val stoneType: StoneType,
) {
    constructor(column: Int, row: Int, board: Board, stoneType: StoneType) : this(
        Position(column, row, board),
        stoneType,
    )

    fun isSamePosition(
        column: Int,
        row: Int,
    ) = position.column.value == column && position.row.value == row
}
