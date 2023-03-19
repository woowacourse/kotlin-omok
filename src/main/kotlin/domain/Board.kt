package domain

class Board(private val stones: Stones) {
    fun repeatTurn(coordinateReader: CoordinateReader) {
        var currentColor = Color.BLACK
        while (true) {
            processTurn(currentColor, coordinateReader)
            if (stones.isWinPlace()) break
            currentColor = currentColor.turnColor()
        }
    }

    private fun processTurn(color: Color, coordinateReader: CoordinateReader) {
        val coordinate = coordinateReader.read(color)
        val stone = Stone(color, coordinate)
        if (!stones.validateRenju(stone)) {
            return processTurn(color, coordinateReader)
        }
        stones.place(stone)
    }

    fun getLastColor(): Color {
        stones.validateEmptyStones()
        return stones.value.last().color
    }

    companion object {
        val BOARD_END_POINT = Point(15, 15)
        val BOARD_START_POINT = Point(0, 0)
    }
}
