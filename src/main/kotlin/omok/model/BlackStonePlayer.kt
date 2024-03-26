package omok.model

class BlackStonePlayer(override val board: Board) : Player() {
    override val color: Color = Color.BLACK

    init {
        board.addStones(stones)
    }

    private val rule: Rule
        get() = RenjuRuleAdapter()

    override fun isWin(): Boolean {
        return rule.isBlackWin(stones, stones.lastStone().point)
    }

    override fun add(point: Point) {
        val stone = Stone(point, color)
        require(!rule.isInValid(stones, stone)) { "렌주룰을 어겼습니다." }
        board.checkDuplicate(stone)

        stones.add(stone)
    }
}
