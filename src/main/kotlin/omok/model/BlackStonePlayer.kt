package omok.model

class BlackStonePlayer(override val board: Board) : Player() {
    override val color: Color = Color.BLACK

    private val rule: Rule
        get() = RenjuRuleAdapter()

    override fun isWin(): Boolean {
        return rule.isBlackWin(blackStones(), requireLastStone())
    }

    private fun blackStones() = stones().filter { it.color == color }
}
