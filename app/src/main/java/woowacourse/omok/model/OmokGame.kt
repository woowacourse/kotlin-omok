package woowacourse.omok.model

class OmokGame(private val gameEventListener: GameEventListener) {
    private var _board = Board()
    val board: Board
        get() = _board

    var gameEnded: Boolean = false
        private set

    fun setStonesOnBoard(stones: List<Stone>) {
        board.setStones(stones)
    }

    fun getNextTurn(): Color {
        return board.getNextTurn()
    }

    fun placeStoneAtPosition(
        row: Int,
        col: Int,
    ): Boolean {
        val thisTurn = board.getNextTurn()
        val coordinate = Coordinate(Row(row), Column(col))
        val state = board.takeTurn(thisTurn, coordinate)
        checkForbiddenMove(state)
        checkGameFinished()
        return state.checkPlacementSuccess()
    }

    private fun checkForbiddenMove(state: StoneState) {
        if (state == StoneState.FORBIDDEN) {
            gameEventListener.onForbiddenStone(state)
        }
    }

    private fun checkGameFinished() {
        if (!board.isPlaying()) {
            gameEnded = true
            gameEventListener.onGameEnd(board.getWinner())
        }
    }

    fun getStoneOrder(): Int {
        return board.getLastStoneOrder()
    }

    fun getLastMove(): Coordinate? {
        return board.getLastStoneCoordinate()
    }

    fun getWinner(): Color {
        return board.getWinner()
    }

    fun restartGame() {
        board.resetBoard()
        gameEnded = false
    }
}