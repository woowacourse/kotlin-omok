package woowacourse.omok.util

import woowacourse.omok.R
import woowacourse.omok.model.Position
import woowacourse.omok.model.StoneColor

fun convertIndexToPosition(index: Int): Position {
    val x = index % 15 + 1
    val y = (224 - index) / 15 + 1
    return Position.of(x, y)
}

fun mapStoneColorToDrawable(color: StoneColor): Int {
    return when (color) {
        StoneColor.BLACK -> R.drawable.black_stone
        StoneColor.WHITE -> R.drawable.white_stone
    }
}
