package domain

class OmokGame(val board: Board) {
    var currentColor: Color = INITIAL_COLOR
        private set
    private var currentState: State = State.Running

    init {
        changeCurrentColor()
    }

    private fun changeStateFinished() {
        if (board.getWinnerColor() != null) currentState = State.Finished
    }

    fun getWinnerColor(): Color? {
        return board.getWinnerColor()
    }

    fun placeTo(stone: Stone): Boolean {
        if (!board.canPlace(stone)) return false
        board.placeStone(stone)
        changeCurrentColor()
        changeStateFinished()
        return true
    }

    fun getStone(position: Position): Stone {
        return Stone(currentColor, position)
    }

    private fun changeCurrentColor() {
        val lastStone = board.stones.getLastStone()
        lastStone?.let { lastStone ->
            currentColor = when (lastStone.color) {
                Color.BLACK -> Color.WHITE
                Color.WHITE -> Color.BLACK
            }
        }
    }

    fun isRunning(): Boolean {
        return currentState == State.Running
    }

    fun resetGame() {
        board.removeAllStones()
        currentState = State.Running
        currentColor = INITIAL_COLOR
    }

    companion object {
        private val INITIAL_COLOR = Color.BLACK
    }
}
