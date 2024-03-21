package omok.model

class BlackStonePlayer : Player() {
    private val rule: Rule
        get() = RuleAdapter(stones, Color.BLACK)

    override fun checkContinuity(stone: Stone): Boolean {
        val directions =
            arrayOf(
                Point(0, 1),
                Point(1, 0),
                Point(1, 1),
                Point(-1, 1),
            )

        directions.forEach { direction ->
            var count = 1
            count += countStones(stone.point, direction)
            count += countStones(stone.point, Point(-direction.row, -direction.col))

            if (count == 5) return true
        }
        return false
    }

    override fun add(stone: Stone) {
        require(!rule.checkThreeThree(stone)) { "33이다 이녀석아" }
        require(!rule.checkFourFour(stone)) { "44다 이녀석아" }
        require(!rule.checkMoreThanFive(stone)) { "장목이다 이녀석아" }

        stones.add(stone)
    }
}
