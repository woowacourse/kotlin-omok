package view

import domain.stone.Color

const val BLACK = "흑"
const val WHITE = "백"

fun Color.toColorName(): String = when (this) {
    Color.White -> WHITE
    Color.Black -> BLACK
}
