package domain

class Board(private val players: Players, private val stones: Stones) {
    fun repeatTurn(coordinateReader: CoordinateReader): Player {
        return players.repeatTurn(stones, coordinateReader)
    }

    companion object {
        val BOARD_SIZE = Point(15, 15)
        val BOARD_START_POINT = Point(0, 0)
    }
}
