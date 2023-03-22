package woowacourse.omok

import domain.stone.Color
import domain.stone.Point

fun String.toColor(): Color {
    return when (this) {
        "흑" -> Color.Black
        "백" -> Color.White
        else -> Color.Black
    }
}

private const val RANGE_UNIT = 15

fun Point.toIndex(): Int = (RANGE_UNIT * y) + x

fun Int.toPoint(): Point =
    Point(this % RANGE_UNIT, this / RANGE_UNIT)

fun Color.toResourceId(): Int {
    return when (this) {
        is Color.Black -> R.drawable.black_stone
        is Color.White -> R.drawable.white_stone
    }
}

fun Color.toName(): String {
    return when (this) {
        is Color.Black -> "흑"
        is Color.White -> "백"
    }
}
