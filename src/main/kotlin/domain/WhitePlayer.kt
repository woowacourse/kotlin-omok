package domain

class WhitePlayer(override val color: Color = Color.WHITE) : Player() {
    override fun validateRenju(stones: Stones, stone: Stone): Boolean = true
}
