package woowacourse.omok

import domain.stone.Point

const val RANGE_UNIT = 15

fun Int.toPoint(): Point =
    Point((this % RANGE_UNIT) + 1, (this / RANGE_UNIT) + 1)

fun Point.toIndex(): Int = (RANGE_UNIT * (y - 1)) + (x - 1)
