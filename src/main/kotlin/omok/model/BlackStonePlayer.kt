package omok.model

class BlackStonePlayer : Player() {
    private val rule = RenjuRuleAdapter()

    override fun checkContinuity(stone: Stone): Boolean {
        directions.forEach { direction ->
            var count = 1

            count += countStones(stone.point, direction)
            count += countStones(stone.point, listOf(-direction[0], -direction[1]))

            if (count == 5) return true
        }
        return false
    }

    override fun add(stone: Stone) {
        require(!rule.isInValid(stones, stone)) { "렌주룰을 어겼습니다." }

        stones.add(stone)
    }

    fun isValidPoint(point: Point): Boolean {
        return !rule.isInValid(stones, Stone(point, Color.BLACK), generateCustomBoard(stones))
    }

    private fun generateCustomBoard(stones: Stones): Array<Array<Int>> {
        val libraryBoard =
            Array(Board.getSize()) {
                Array(Board.getSize()) { 0 }
            }
        stones.stones.forEach {
            when (it.color) {
                Color.BLACK -> libraryBoard[it.point.col][it.point.row] = 1
                Color.WHITE -> libraryBoard[it.point.col][it.point.row] = 2
            }
        }
        return libraryBoard
    }

    companion object {
        val directions =
            arrayOf(
                Point(0, 1),
                Point(1, 0),
                Point(1, 1),
                Point(-1, 1),
            )
        private val directions = listOf(listOf(1, 0), listOf(1, 1), listOf(0, 1), listOf(1, -1))
    }
}
