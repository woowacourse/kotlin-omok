package omok.model

class WhiteStonePlayer(override val board: Board) : Player() {
    override val color: Color = Color.WHITE

    private val rule: Rule
        get() = RenjuRuleAdapter()

    override fun isWin(): Boolean {
        return rule.isWhiteWin(whiteStones(), requireLastStone())
    }

    private fun whiteStones() = stones().filter { it.color == color }
}
