package omok.model

class BlackStonePlayer(override val stones: Stones) : Player() {
    override val color: Color = Color.BLACK

    private val rule: Rule
        get() = RenjuRuleAdapter()

    override fun isWin(): Boolean {
        return rule.isBlackWin(blackStones(), requireLastStone().point)
    }

    private fun blackStones() = stones().filter { it.color == color }
}
