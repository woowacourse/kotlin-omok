package domain

class OmokGame(val board: Board) {
    var currentColor: Color = INITIAL_COLOR
    fun getWinnerColorPhase(
        getCurrentResult: (OmokGame) -> Unit,
        getPosition: () -> Position
    ): Color {
        val stone = getStone(getCurrentResult, getPosition)
        board.placeStone(stone)
        return board.getWinnerColor() ?: getWinnerColorPhase(getCurrentResult, getPosition)
    }

    private fun getStone(showCurrentState: (OmokGame) -> Unit, getPosition: () -> Position): Stone {
        showCurrentState(this)
        val stone = Stone(currentColor, getPosition())
        if (!board.isEmpty(stone)) return getStone(showCurrentState, getPosition)
        currentColor = nextColor(currentColor)
        return stone
    }

    private fun nextColor(color: Color): Color {
        return when (color) {
            Color.BLACK -> Color.WHITE
            Color.WHITE -> Color.BLACK
        }
    }

    companion object {
        private val INITIAL_COLOR = Color.BLACK
    }
}
