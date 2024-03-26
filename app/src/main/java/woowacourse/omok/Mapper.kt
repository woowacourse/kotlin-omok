package woowacourse.omok

import omok.model.Position

fun indexToPosition(index: Int): Position {
    val x = index % 15 + 1
    val y = (224 - index) / 15 + 1
    return Position.of(x, y)
}
