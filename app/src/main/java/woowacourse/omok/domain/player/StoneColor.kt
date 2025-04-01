package woowacourse.omok.domain.player

enum class StoneColor {
    BLACK,
    WHITE,
    ;

    fun reversed(): StoneColor =
        when (this) {
            BLACK -> WHITE
            WHITE -> BLACK
        }
}
