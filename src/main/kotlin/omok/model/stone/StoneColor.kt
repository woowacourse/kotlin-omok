package omok.model.stone

enum class StoneColor {
    WHITE,
    BLACK,
    ;

    companion object {
        fun StoneColor.next(): StoneColor {
            if (this == WHITE) return BLACK
            return WHITE
        }
    }
}
