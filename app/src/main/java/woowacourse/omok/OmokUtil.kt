package woowacourse.omok

import domain.stone.Color
import domain.stone.Point

const val RANGE_UNIT = 15

fun Color.toName(): String {
    return when (this) {
        is Color.Black -> BLACK_DESCRIPTION
        is Color.White -> WHITE_DESCRIPTION
    }
}

fun Int.toPoint(): Point =
    Point((this % RANGE_UNIT)+1, (this / RANGE_UNIT)+1)

const val BLACK_DESCRIPTION = "흑"
const val WHITE_DESCRIPTION = "백"
