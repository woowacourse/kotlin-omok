package omok.model

enum class PlayerColor {
    BLACK,
    WHITE,
    ;

    fun reverse(): PlayerColor {
        return when (this) {
            BLACK -> WHITE
            WHITE -> BLACK
        }
    }
}
