package domain

class OmokGame(val board: Board) {
    var currentColor: Color = INITIAL_COLOR
    fun getWinnerColorPhase(
        stone: Stone?,
    ): Color? {
        if (stone == null) return null
        board.placeStone(stone)
        currentColor = nextColor(currentColor)
        return board.getWinnerColor()
    }

    fun getStone(getPosition: () -> Position): Stone? {
        val stone = Stone(currentColor, getPosition())
        if (!board.isEmpty(stone)) return null
        return stone
    }

    fun nextColor(color: Color): Color {
        return when (color) {
            Color.BLACK -> Color.WHITE
            Color.WHITE -> Color.BLACK
        }
    }

    fun resetGame() {
        board.removeAllStones()
        currentColor = Color.BLACK
    }

    companion object {
        private val INITIAL_COLOR = Color.BLACK
    }
}
