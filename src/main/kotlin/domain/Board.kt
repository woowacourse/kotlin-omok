package domain

class Board(private val players: Players, private val stones: Stones) {
    fun repeatTurn(coordinateReader: CoordinateReader, winEvent: (winner: Player) -> Unit) {
        while (true) {
            val currentTurnPlayer = players.currentTurn()
            val stone = stones.makeValidatedStone(currentTurnPlayer, coordinateReader)
            stones.place(stone)
            if (stones.isWinPlace(stone)) {
                winEvent(currentTurnPlayer)
                break
            }
        }
    }

    companion object {
        val BOARD_SIZE = Point(15, 15)
        val BOARD_START_POINT = Point(0, 0)
    }
}
