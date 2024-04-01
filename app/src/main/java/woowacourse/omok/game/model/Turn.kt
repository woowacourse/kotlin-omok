package omok.model

data class Turn(private var turn: Color = Color.BLACK) {
    fun next() {
        turn =
            if (turn == Color.BLACK) {
                Color.WHITE
            } else {
                Color.BLACK
            }
    }

    fun reset() {
        turn = Color.WHITE
    }

    fun color(): Color = turn

    fun isBlack(): Boolean = turn.isBLACK()

    fun isWhite(): Boolean = turn.isWhite()
}
