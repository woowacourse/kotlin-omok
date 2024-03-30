package woowacourse.omok.adapter

import omok.model.position.Column
import omok.model.position.Position
import omok.model.position.Row
import omok.model.stone.StoneType
import woowacourse.omok.R

object OmokBoardAdapter {
    private const val BOARD_SIZE = 15

    fun convertIndexToPosition(index: Int): Position {
        val rowValue = index / BOARD_SIZE
        val columnValue = index % BOARD_SIZE
        return Position(Row(rowValue), Column(columnValue))
    }

    fun convertStoneTypeToDrawable(stoneType: StoneType): Int {
        return when (stoneType) {
            StoneType.BLACK_STONE -> R.drawable.black_stone
            StoneType.WHITE_STONE -> R.drawable.white_stone
            StoneType.NONE -> 0
        }
    }
}
