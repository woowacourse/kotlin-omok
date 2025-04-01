package woowacourse.omok.model.stone

enum class StoneColor {
    BLACK,
    WHITE,
    ;

    fun reverse(): StoneColor =
        when (this) {
            BLACK -> WHITE
            WHITE -> BLACK
        }
}
