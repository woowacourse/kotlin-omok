package omok.model

class OmokGame(
    private var turn: Turn = BlackTurn(),
    private val board: Board = Board(),
) {
    fun play(
        updateBoard: (Board) -> Unit,
        updateTurn: (Turn) -> Unit,
        getPoint: () -> Point,
    ) {
        while (!isGameFinished()) {
            updateBoard(board)
            updateTurn(turn)
            val point = getPoint()
            turn = turn.putStone(point, board)
        }
        updateTurn(turn)
    }

    fun tryPlayTurn(
        updateTurn: (Turn) -> Unit,
        getPoint: () -> Point,
    ): Boolean {
        val point = getPoint()
        val nextTurn = turn.putStone(point, board)
        if (turn != nextTurn) {
            turn = nextTurn
            updateTurn(turn)
            return true
        }
        return false
    }

    fun isGameFinished(): Boolean = turn is FinishedTurn
}
