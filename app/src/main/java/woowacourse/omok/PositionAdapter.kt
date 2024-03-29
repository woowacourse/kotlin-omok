package woowacourse.omok

import omok.model.position.Column
import omok.model.position.Position
import omok.model.position.Row

object PositionAdapter {
    private const val BOARD_SIZE = 15

    fun convertIndexToPosition(index: Int): Position {
        val rowValue = index / BOARD_SIZE
        val columnValue = index % BOARD_SIZE
        return Position(Row(rowValue), Column(columnValue))
    }
}
