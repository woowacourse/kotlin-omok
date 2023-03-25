package woowacourse.omok.model

import omok.model.stone.GoStoneColor

enum class GoStoneColorNumber(val number: Int) {
    BLACK(1),
    WHITE(2);

    companion object {
        fun convertGoStoneColorNumber(goStoneColor: GoStoneColor) =
            when (goStoneColor) {
                GoStoneColor.BLACK -> BLACK
                GoStoneColor.WHITE -> WHITE
            }
    }
}
