package woowacourse.omok.model

enum class Color {
    BLACK,
    WHITE,
    ;

    companion object {
        fun getReversedColor(color: Color): Color {
            return when (color) {
                BLACK -> WHITE
                WHITE -> BLACK
            }
        }
    }
}
