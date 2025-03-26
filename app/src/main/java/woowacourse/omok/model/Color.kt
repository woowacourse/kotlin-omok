package woowacourse.omok.model

enum class Color {
    BLACK,
    WHITE,
    ;

    fun reverse(): Color {
        return when (this) {
            BLACK -> WHITE
            WHITE -> BLACK
        }
    }
}
