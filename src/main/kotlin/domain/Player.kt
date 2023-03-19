package domain

sealed class Player {
    abstract val color: Color
    class WhitePlayer(override val color: Color = Color.WHITE) : Player()
    class BlackPlayer(override val color: Color = Color.BLACK) : Player()
}
