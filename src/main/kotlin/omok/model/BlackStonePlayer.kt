package omok.model

import java.lang.IllegalStateException

class BlackStonePlayer(override val board: Board) : Player() {
    override val color: Color = Color.BLACK

    init {
        board.addStones(stones)
    }

    private val rule: Rule
        get() = RenjuRuleAdapter()

    override fun checkContinuity(): Boolean {
        val lastStone = stones.lastStone() ?: throw IllegalStateException("돌을 먼저 놓아주세요")
        directions.forEach { direction ->
            var count = 1
            count += countStones(lastStone.point, direction)
            count += countStones(lastStone.point, Point(-direction.row, -direction.col))

            if (count == 5) return true
        }
        return false
    }

    override fun add(point: Point) {
        val stone = Stone(point, color)
        require(!rule.isInValid(stones, stone)) { "렌주룰을 어겼습니다." }
        board.checkDuplicate(stone)

        stones.add(stone)
    }
}
