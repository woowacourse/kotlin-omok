package omok.model

class WhiteStonePlayer(override val stones: Stones) : Player() {
    override val color: Color = Color.WHITE

    private val rule: Rule
        get() = RenjuRuleAdapter()

    override fun isWin(): Boolean {
        return rule.isWhiteWin(whiteStones(), requireLastStone().point)
    }

    private fun whiteStones() = stones().filter { it.color == color }
}
