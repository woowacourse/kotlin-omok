package omok.model

data class Turn(private var color: Color) {
    fun next() {
        color = if (color.isWhite()) Color.BLACK else Color.WHITE
    }

    fun isBlack(): Boolean = color.isBLACK()

    fun isWhite(): Boolean = color.isWhite()
}
