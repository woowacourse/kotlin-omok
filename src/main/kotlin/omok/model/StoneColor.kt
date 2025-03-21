package omok.model

enum class StoneColor {
    BLACK,
    WHITE,
    ;

    fun reverse(): StoneColor {
        return when (this) {
            BLACK -> WHITE
            WHITE -> BLACK
        }
    }
}
