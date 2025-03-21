package omok.domain.stone

enum class StoneColor {
    BLACK,
    WHITE,
    ;

    fun toggle(): StoneColor = if (this == BLACK) WHITE else BLACK
}
