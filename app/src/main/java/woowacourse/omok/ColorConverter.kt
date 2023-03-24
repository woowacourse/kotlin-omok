package woowacourse.omok

import domain.stone.Color

const val BLACK_DESCRIPTION = "흑"
const val WHITE_DESCRIPTION = "백"

fun Color.toStoneImage(): Int {
    return when (this) {
        is Color.Black -> R.drawable.custom_black_stone
        is Color.White -> R.drawable.custom_white_stone
    }
}

fun Color.toName(): String {
    return when (this) {
        is Color.Black -> BLACK_DESCRIPTION
        is Color.White -> WHITE_DESCRIPTION
    }
}
