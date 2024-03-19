package omok.model

class Board(var layout: Array<Array<StoneType>> = Array(BOARD_SIZE) { Array(BOARD_SIZE) { StoneType.EMPTY } }) {
    private var gameState: GameState = GameState.BlackTurn()

    fun play(
        onTurn: (GameState) -> Unit,
        onRead: () -> Position,
        onShow: (Board) -> Unit,
    ) {
        while (true) {
            placeStone(onTurn, onRead, onShow)
        }
    }

    private fun placeStone(
        onTurn: (GameState) -> Unit,
        onRead: () -> Position,
        onShow: (Board) -> Unit,
    ) {
        onTurn(gameState)
        val position = onRead()
        layout[position.coordinate.x][position.coordinate.y] = gameState.currentStone
        gameState = gameState.updateState(position)
        onShow(this)
    }

    companion object {
        private const val BOARD_SIZE = 15
    }
}
