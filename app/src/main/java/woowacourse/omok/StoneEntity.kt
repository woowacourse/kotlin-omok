package woowacourse.omok

import omok.model.Color
import omok.model.Stone

data class StoneEntity(
    val positionRow: Int,
    val positionCol: Int,
    val color: Int,
) {
    fun toStone() = Stone(positionRow, positionCol, color.toColor())

    private fun Int.toColor() = if (this == 1) Color.BLACK else Color.WHITE
}
