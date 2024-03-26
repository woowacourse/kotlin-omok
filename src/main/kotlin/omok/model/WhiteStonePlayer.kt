package omok.model

class WhiteStonePlayer(override val board: Board) : Player() {
    override val color: Color = Color.WHITE

    private val rule: Rule
        get() = RenjuRuleAdapter()

    init {
        board.addStones(stones)
    }

    override fun isWin(): Boolean {
        return rule.isWhiteWin(stones, stones.lastStone().point)
    }
}
