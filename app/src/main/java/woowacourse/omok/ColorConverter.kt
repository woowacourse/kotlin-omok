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

fun String.descriptionToColor(): Color {
    return when (this) {
        BLACK_DESCRIPTION -> Color.Black
        WHITE_DESCRIPTION -> Color.White
        else -> throw IllegalArgumentException(COLOR_ERROR)
    }
}

private const val COLOR_ERROR = "[ERROR] 알 수 없는 색깔입니다."
