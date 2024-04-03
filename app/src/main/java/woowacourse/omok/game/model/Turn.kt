package woowacourse.omok.game.model

data class Turn(private var turn: Color? = Color.WHITE) {
    fun next() {
        turn =
            if (turn == Color.BLACK) {
                Color.WHITE
            } else {
                Color.BLACK
            }
    }

    fun currentTurn(): Turn {
        this.next()
        return this
    }

    fun reset() {
        turn = Color.BLACK
    }

    fun color(): Color = turn!!

    fun isBlack(): Boolean = turn!!.isBlack()

    fun isWhite(): Boolean = turn!!.isWhite()
}
