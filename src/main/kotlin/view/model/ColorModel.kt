package view.model

import domain.stone.StoneColor

object ColorModel {
    private const val BLACK = "흑"
    private const val WHITE = "백"

    fun getString(color: StoneColor): String = when (color) {
        StoneColor.BLACK -> BLACK
        StoneColor.WHITE -> WHITE
    }
}
