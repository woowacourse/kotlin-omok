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

        fun convertGoStoneColor(colorNumber: Int) =
            when (colorNumber) {
                1 -> GoStoneColor.BLACK
                2 -> GoStoneColor.WHITE
                else -> throw IllegalArgumentException("잘못된 colorNumber 입니다. (잘못된 값: $colorNumber)")
            }
    }
}
