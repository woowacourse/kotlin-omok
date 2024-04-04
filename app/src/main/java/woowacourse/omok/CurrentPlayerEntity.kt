package woowacourse.omok

import omok.model.Color

data class CurrentPlayerEntity(
    val color: Int,
) {
    fun toColor() = if (color == 1) Color.BLACK else Color.WHITE
}
