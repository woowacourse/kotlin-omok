package woowacourse.omok.model.board

import woowacourse.omok.model.Stone
import woowacourse.omok.model.position.Position

sealed class BoardCell {
    abstract val position: Position

    data class EmptyCell(
        override val position: Position,
    ) : BoardCell()

    data class ExistsCell(
        override val position: Position,
        val stone: Stone,
    ) : BoardCell()

    fun isEmpty(): Boolean = this is EmptyCell

    fun replace(stone: Stone): BoardCell = ExistsCell(position, stone)
}
