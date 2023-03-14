package domain

class Board(private val players: Players, private val stones: Stones) {
    fun repeatTurn(coordinateReader: CoordinateReader) {
        players.repeatTurn(stones, coordinateReader)
    }
}
