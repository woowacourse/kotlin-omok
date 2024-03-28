package woowacourse.omok.domain.model

class OmokGame(
    private var turn: Turn = BlackTurn(),
    private val board: Board = Board(),
) {
    fun tryPlayTurn(
        updateBoard: (Board) -> Unit,
        updateTurn: (Turn, Stone?) -> Unit,
        getPoint: () -> Point,
    ): Boolean {
        val point = getPoint()
        val nextTurn = turn.putStone(point, board)
        if (turn != nextTurn) {
            updateBoard(board)
            turn = nextTurn
            updateTurn(turn, board.latestStone)
            return true
        }
        return false
    }

    fun isGameFinished(): Boolean = turn is FinishedTurn
}
