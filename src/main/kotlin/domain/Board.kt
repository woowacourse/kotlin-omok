package domain

class Board(private val players: Players, private val stones: Stones) {
    fun repeatTurn(coordinateReader: CoordinateReader) {
        var index = 0
        while (true) {
            val stone = players.value[index].place(stones, coordinateReader)
            if (stones.isWinPlace(stone)) break
            index = turnPlayer(index)
        }
    }

    private fun turnPlayer(index: Int): Int = if (index == players.value.size - LAST_TURN) INITIAL_TURN else index + NEXT_TURN

    fun getLastColor(): Color {
        require(stones.value.isNotEmpty()) { println(ERROR_STONES_EMPTY) }
        return stones.value.last().color
    }

    companion object {
        private const val INITIAL_TURN = 0
        private const val LAST_TURN = 1
        private const val NEXT_TURN = 1
        val BOARD_END_POINT = Point(15, 15)
        val BOARD_START_POINT = Point(0, 0)
        private const val ERROR_STONES_EMPTY = "놓여진 바둑돌이 없습니다"
    }
}
