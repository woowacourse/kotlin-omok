package domain

class WhitePlayer(override val color: Color = Color.WHITE) : Player() {
    override fun validateOmokRule(stones: Stones, stone: Stone): Boolean = true
}
