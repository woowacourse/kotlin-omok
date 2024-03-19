package omok.model

class Board(var layout: Array<Array<StoneType>> = Array(BOARD_SIZE) { Array(BOARD_SIZE) { StoneType.EMPTY } }) {
    private var gameState = GameState.BlackTurn

    fun placeStone(
        onRead: () -> Position,
        onShow: (Board) -> Unit,
    ) {
        val position = onRead()
        layout[position.x][position.y] = gameState.currentStone
        gameState.updateState()
        onShow(this)
    }

    companion object {
        private const val BOARD_SIZE = 15
    }
}
