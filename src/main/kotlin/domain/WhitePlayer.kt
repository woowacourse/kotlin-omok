package domain

class WhitePlayer(override val color: Color = Color.WHITE) : Player() {
    override fun validateOmokRule(stone: Stone, omokRule: OmokRule): Boolean = true
}
