package domain

class Players(private val value: List<Player>) {

    fun repeatTurn(stones: Stones, coordinateReader: CoordinateReader): Player {
        var index = 0
        while (true) {
            val stone = value[index].place(stones, coordinateReader)
            if (isWinPlace(stone, stones)) return value[index]
            index = turnPlayer(index)
        }
    }

    private fun isWinPlace(stone: Stone, stones: Stones): Boolean {
        return stones.renjuRule.findScore(stone) >= WINNING_CONDITION
    }

    private fun turnPlayer(index: Int): Int = if (index == value.size - LAST_TURN) INITIAL_TURN else index + NEXT_TURN

    companion object {
        private const val INITIAL_TURN = 0
        private const val LAST_TURN = 1
        private const val NEXT_TURN = 1
        private const val WINNING_CONDITION = 4
    }
}
