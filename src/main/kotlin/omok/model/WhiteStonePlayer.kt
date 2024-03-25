package omok.model

class WhiteStonePlayer : Player() {
    override fun checkContinuity(stone: Stone): Boolean {
        directions.forEach { direction ->
            var count = 1

            count += countStones(stone.point, direction)
            count += countStones(stone.point, listOf(-direction[0], -direction[1]))

            if (count >= 5) return true
        }
        return false
    }

    companion object {
        private val directions = listOf(listOf(1, 0), listOf(1, 1), listOf(0, 1), listOf(1, -1))
    }
}
