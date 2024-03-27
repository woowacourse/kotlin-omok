package omok.model

class BlackStones(override val board: Board) : Stones() {
    override val color: Color = Color.BLACK

    private val rule: Rule
        get() = RenjuRuleAdapter()

    override fun isWin(): Boolean {
        return rule.isBlackWin(blackStones(), requireLastStone())
    }

    private fun blackStones() = stones().filter { it.color == color }
}
