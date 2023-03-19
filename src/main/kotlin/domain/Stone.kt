package domain.domain

data class Stone(private val color: Color, val position: Position2) {

    fun isBlack(): Boolean {
        return color == Color.BLACK
    }

    fun isWhite(): Boolean {
        return color == Color.WHITE
    }
}
