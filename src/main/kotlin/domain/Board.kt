package domain

class Board(private val players: Players, private val stones: Stones) {
    fun repeatTurn(coordinateReader: CoordinateReader) {
        players.repeatTurn(stones, coordinateReader)
    }

    fun getLastColor(): Color {
        require(stones.value.isNotEmpty()) { println(ERROR_STONES_EMPTY) }
        return stones.value.last().color
    }

    companion object {
        val BOARD_END_POINT = Point(15, 15)
        val BOARD_START_POINT = Point(0, 0)
        private const val ERROR_STONES_EMPTY = "놓여진 바둑돌이 없습니다"
    }
}
